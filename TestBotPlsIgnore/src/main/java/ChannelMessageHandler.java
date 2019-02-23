import api.FactApi;
import api.RedditApi;
import api.TwitchApi;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static api.MapConstants.*;

public class ChannelMessageHandler {
    private FactApi factApi = new FactApi();
    private RedditApi redditApi = new RedditApi();
    private TwitchApi twitchApi = new TwitchApi();
    private String botName = "Test Bot Pls Ignore";
    private String[] redditFilters = { "top", "new", "hot", "controversial", "rising" };

    private MessageReceivedEvent event;
    private String msg;
    private MessageChannel channel;
    private User author;
    private List<User> mentions;

    public ChannelMessageHandler(MessageReceivedEvent event) {
        this.event = event;
        this.author = event.getAuthor();
        this.msg = event.getMessage().getContentRaw().toLowerCase();
        this.channel = event.getChannel();
        this.mentions = event.getMessage().getMentionedUsers();
    }

    public void handleMsg() {
        String[] msgSections = msg.split(" ");

        if (msg.equals("!marco")) {
            marcoMsg();
        } else if (msg.startsWith("!fact")) {
            if (msgSections.length > 1 && isNumber(msgSections[1])) {
                factMsg(Integer.parseInt(msgSections[1]));
            } else {
                factMsg(1);
            }
        } else if (msg.startsWith("!fuck you") && mentions.size() > 0) {
            fuckYouMsg();
        } else if (msg.startsWith("!reddit") && msgSections.length == 3 && isFilter(msgSections[1])) {
            filterRedditMsg(msgSections[2], msgSections[1]);
        } else if (msg.startsWith("!reddit search") && msgSections.length > 2) {
            List<String> params = new ArrayList<>();

            for (int i = 2; i < msgSections.length; i++) {
                params.add(msgSections[i]);
            }

            searchRedditMsg(params);
        } else if (msg.startsWith("!twitch status") && msgSections.length == 3) {
            twitchStreamMsg(msgSections[2]);
        } else if (msg.startsWith("!clear")) {
            if (msgSections.length > 1 && isNumber(msgSections[1])) {
                clearMsg(Integer.parseInt(msgSections[1]));
            } else {
                clearMsg(10);
            }
        } else if (msg.startsWith("!reddit random") && msgSections.length == 3) {
            randomRedditMsg(msgSections[2]);
        } else if (msg.equals("!commands")) {
            commandMsg();
        } else if (msg.equals("!poggers")) {
            poggersMsg();
        } else if (msg.equals("!thinking")) {
            thinkingMsg();
        } else if (msg.equals("!stupid bot")) {
            stupidMsg();
        } else if (msg.equals("!baka bot")) {
            bakaMsg();
        }
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFilter(String input) {
        for (String filter : redditFilters) {
            if (filter.equals(input)) {
                return true;
            }
        }

        return false;
    }

    private boolean doesContainName(List<User> users, String name) {
        return users.stream().anyMatch(User -> User.getName().equals(name));
    }

    private void commandMsg() {
        String channelMsg = "Commands are not case sensitive. <required> [optional]"
                + "\n`!marco`"
                + "\n`!fact [amount]`"
                + "\n`!fuck you <1 or more mentions>`"
                + "\n`!reddit <filter> <subreddit> (filters: top, new, hot, controversial, rising)`"
                + "\n`!reddit search <subreddit> <query>`"
                + "\n`!reddit random <subreddit>`"
                + "\n`!twitch status <channel>`"
                + "\n`!poggers`"
                + "\n`!thinking`"
                + "\n`!stupid bot`"
                + "\n`!baka bot`"
                + "\n`!clear <amount>`";

        channel.sendMessage(channelMsg).queue();
    }

    private void marcoMsg() {
        channel.sendMessage("Polo!").queue();
    }

    private void factMsg(int amountOfFacts) {
        if (amountOfFacts > 10) {
            amountOfFacts = 10;
        }

        channel.sendMessage("Retrieving " + amountOfFacts + " random facts... (max 10)").queue();
        List<String> facts = new ArrayList<String>();
        for (int i = 0; i < amountOfFacts; i++) {
            facts.add(factApi.getFact());
        }

        StringBuilder channelMsg = new StringBuilder(facts.get(0));
        for (int i = 1; i < facts.size(); i++) {
            channelMsg.append("\n").append(facts.get(i));
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void fuckYouMsg() {
        String channelMsg = "";

        if (doesContainName(mentions, botName)) {
            channelMsg = "Fuck me? No, fuck you " + author.getName() + "!";
        } else {
            channelMsg = "Yo " + mentions.get(0).getName();

            if (mentions.size() > 1) {
                for (int i = 1; i < mentions.size() - 1; i++) {
                    channelMsg += (", " + mentions.get(i).getName());
                }
                channelMsg += " and " + mentions.get(mentions.size() - 1).getName();
            }

            channelMsg += ", you gonna take that?";
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void filterRedditMsg(String subreddit, String filter) {
        List<String> results = redditApi.filterPostsSubreddit(subreddit, filter);
        String channelMsg = "";
        if (!results.isEmpty()) {
            if (results.contains("Error retrieving results")) {
                channelMsg = "Error retrieving results";
            } else {
                channelMsg = "Showing 5 posts of r/" + subreddit + " under " + filter;
                for (int i = 0; i < results.size(); i++) {
                    channelMsg += "\n\n" + results.get(i);
                }
            }
        } else {
            channelMsg = "No results found. r/" + subreddit + " may not exist or cannot be accessed";
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void searchRedditMsg(List<String> params) {
        String subreddit = params.get(0);
        String query = "";
        if (params.size() > 1) {
            query = params.get(1);
            for (int i = 2; i < params.size(); i++) {
                query += " " + params.get(i);
            }
        }

        List<String> results = redditApi.searchSubreddit(subreddit, query);
        String channelMsg = "";

        if (!results.isEmpty()) {
            if (results.contains("Error retrieving results")) {
                channelMsg = "Error retrieving results";
            } else {
                channelMsg = "Showing search results for \"" + query + "\" in r/" + subreddit;
                for (int i = 0; i < results.size(); i++) {
                    channelMsg += "\n\n" + results.get(i);
                }
            }
        } else {
            channelMsg = "No results found. r/" + subreddit + " may not exist or cannot be accessed, or you may not have entered a query";
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void twitchStreamMsg(String channelName) {
        Map<String, String> streamStatus = twitchApi.getStreamStatus(channelName);

        String channelMsg = "";

        if (streamStatus.get(STATUS).equals("Error retrieving stream")) {
            channelMsg = streamStatus.get(STATUS);
        } else if (streamStatus.get(STATUS).equals("null")) {
            channelMsg = channelName + " does not exist";
        } else if (streamStatus.get(STATUS).equals("offline")) {
            String name = streamStatus.get(NAME);
            String game = streamStatus.get(GAME);
            String url = streamStatus.get(URL);
            String title = streamStatus.get(TITLE);

            channelMsg = name + " is not currently live :("
                    + "\nLast seen playing: " + game + " [" + title + "]"
                    + "\nStream link: " + url;
        } else {
            String name = streamStatus.get(NAME);
            String viewers = streamStatus.get(VIEWERS);
            String game = streamStatus.get(GAME);
            String url = streamStatus.get(URL);
            String title = streamStatus.get(TITLE);

            channelMsg = name + " is currently live!"
                    + "\nTitle: " + title
                    + "\nCurrently playing: " + game
                    + "\nViewers: " + viewers
                    + "\nStream link: " + url;
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void clearMsg(int amountOfMessages) {
        if (amountOfMessages >= 100) {
            amountOfMessages = 99;
        }
        List<Message> history = channel.getHistory().retrievePast(amountOfMessages + 1).complete();
        System.out.println("clear command requested " + history.toString());
        channel.purgeMessages(history);
    }

    private void randomRedditMsg(String subreddit) {
        List<String> results = redditApi.randomPost(subreddit);
        if (!results.isEmpty()) {
            channel.sendMessage(results.get(0)).queue();

            if (results.size() > 1) {
                channel.sendMessage(results.get(1)).queue();
            }
        } else {
            channel.sendMessage("No results found. r/" + subreddit + " may not exist or cannot be accessed").queue();
        }
    }

    private void poggersMsg() {
        Message eventMsg = event.getMessage();
        eventMsg.addReaction("\uD83C\uDDF5").queue();
        eventMsg.addReaction("\uD83C\uDDF4").queue();
        eventMsg.addReaction("\uD83C\uDDEC").queue();
    }

    private void thinkingMsg() {
        channel.sendFile(new File("thinking.gif")).queue();
    }

    private void stupidMsg() {
        String channelMsg = "What the fuck did you just fucking say about me, you little bitch? I’ll have you know I graduated top of my class in the Navy Seals, and I’ve been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and I’m the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. You’re fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and that’s just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little “clever” comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldn’t, you didn’t, and now you’re paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. You’re fucking dead, kiddo.";
        MessageBuilder builder = new MessageBuilder(channelMsg).setTTS(true);
        channel.sendMessage(builder.build()).queue();
    }

    private void bakaMsg() {
        String channelMsg = "Nani the fuck did you just fucking iimasu about watashi, you chiisai bitch desuka? Watashi’ll have anata know that watashi graduated top of my class in Nihongo 3, and watashi’ve been involved in iroirona Nihongo tutoring sessions, and watashi have over sanbyaku perfect test scores. Watashi am trained in kanji, and watashi is the top letter writer in all of southern California. Anata are nothing to watashi but just another weaboo. Watashi will korosu anata the fuck out with vocabulary the likes of which has never been mimasu’d before on this continent, mark watashino fucking words. Anata thinks anata can get away with hanashimasing that kuso to watashi over the intaaneto? Omou again, fucker. As we hanashimasu, watashi am contacting watashino secret netto of otakus across the USA, and anatano IP is being traced right now so you better junbishimasu for the ame, ujimushi. The ame that korosu’s the pathetic chiisai thing anata calls anatano life. You’re fucking shinimashita’d, akachan.";
        MessageBuilder builder = new MessageBuilder(channelMsg).setTTS(true);
        channel.sendMessage(builder.build()).queue();
    }
}
