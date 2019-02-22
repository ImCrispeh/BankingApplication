import api.FactApi;
import api.RedditApi;
import api.TwitchApi;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static api.MapConstants.*;

public class ChannelMessageHandler {
    private FactApi factApi = new FactApi();
    private RedditApi redditApi = new RedditApi();
    private TwitchApi twitchApi = new TwitchApi();
    private String botName = "Test Bot Pls Ignore";

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
        } else if (msg.equals("!fact")) {
            factMsg();
        } else if (msg.startsWith("!fuck you") && mentions.size() > 0) {
            fuckYouMsg();
        } else if (msg.startsWith("!reddit top") && msgSections.length == 3) {
            topRedditMsg(msgSections[2]);
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

    private boolean doesContainName(List<User> users, String name) {
        return users.stream().anyMatch(User -> User.getName().equals(name));
    }

    private void marcoMsg() {
        channel.sendMessage("Polo!").queue();
    }

    private void factMsg() {
        channel.sendMessage(factApi.getFact()).queue();
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

    private void topRedditMsg(String subreddit) {
        List<String> results = redditApi.topPostsSubreddit(subreddit);
        String channelMsg = "";
        if (!results.isEmpty()) {
            if (results.contains("Error retrieving results")) {
                channelMsg = "Error retrieving results";
            } else {
                channelMsg = "Showing top 5 posts of r/" + subreddit;
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
        List<Message> history = channel.getHistory().retrievePast(amountOfMessages + 1).complete();
        System.out.println("clear command requested " + history.toString());
        channel.purgeMessages(history);
    }
}
