import api.FactApi;
import api.RedditApi;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ChannelMessageHandler {
    private FactApi factApi = new FactApi();
    private RedditApi redditApi = new RedditApi();
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
            topRedditMsg(msg.split(" ")[2]);
        } else if (msg.startsWith("!reddit search") && msgSections.length > 2) {
            List<String> params = new ArrayList<>();

            for (int i = 2; i < msgSections.length; i++) {
                params.add(msgSections[i]);
            }

            searchRedditMsg(params);
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
        if (doesContainName(mentions, botName)) {
            channel.sendMessage("Fuck me? No, fuck you " + author.getName() + "!").queue();
        } else {
            String botMsg = "Yo " + mentions.get(0).getName();

            if (mentions.size() > 1) {
                for (int i = 1; i < mentions.size() - 1; i++) {
                    botMsg += (", " + mentions.get(i).getName());
                }
                botMsg += " and " + mentions.get(mentions.size() - 1).getName();
            }

            botMsg += ", you gonna take that?";
            channel.sendMessage(botMsg).queue();
        }
    }

    private void topRedditMsg(String subreddit) {
        List<String> results = redditApi.topPostsSubreddit(subreddit);
        if (!results.isEmpty()) {
            if (results.contains("Error retrieving results")) {
                channel.sendMessage("Error retrieving results").queue();
            } else {
                String channelMsg = "Showing top 5 posts of r/" + subreddit;
                for (int i = 0; i < results.size(); i++) {
                    channelMsg += "\n\n" + results.get(i);
                }
                channel.sendMessage(channelMsg).queue();
            }
        } else {
            channel.sendMessage("r/" + subreddit + " does not exist or cannot be accessed").queue();
        }
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

        if (!results.isEmpty()) {
            if (results.contains("Error retrieving results")) {
                channel.sendMessage("Error retrieving results").queue();
            } else {
                String channelMsg = "Showing search results for \"" + query + "\" in r/" + subreddit;
                for (int i = 0; i < results.size(); i++) {
                    channelMsg += "\n\n" + results.get(i);
                }
                channel.sendMessage(channelMsg).queue();
            }
        } else {
            channel.sendMessage("r/" + subreddit + " does not exist or cannot be accessed").queue();
        }
    }
}
