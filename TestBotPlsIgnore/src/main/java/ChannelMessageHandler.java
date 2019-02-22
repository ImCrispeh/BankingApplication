import api.FactApi;
import api.RedditApi;
import api.TwitchApi;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static api.MapConstants.*;

public class ChannelMessageHandler {
    private FactApi factApi = new FactApi();
    private RedditApi redditApi = new RedditApi();
    private TwitchApi twitchApi = new TwitchApi();
    private String botName = "Test Bot Pls Ignore";

    private String msg;
    private MessageChannel channel;
    private User author;
    private List<User> mentions;

    public ChannelMessageHandler(String msg, MessageChannel channel, User author, List<User> mentions) {
        this.msg = msg;
        this.channel = channel;
        this.author = author;
        this.mentions = mentions;
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
            channelMsg = "r/" + subreddit + " does not exist or cannot be accessed";
        }

        channel.sendMessage(channelMsg).queue();
    }

    private void searchRedditMsg(List<String> params) {
        String subreddit = params.get(0);
        String query = "";
        for (String param : params) {
            if (param.startsWith("q=")) {
                query = param.substring(2);
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
            channelMsg = "r/" + subreddit + " does not exist or cannot be accessed";
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
}
