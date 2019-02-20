import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class ChannelMessageHandler {
    private FactRetriever factRetriever = new FactRetriever();
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
        if (msg.equals("!marco")) {
            marcoMsg();
        } else if (msg.equals("!fact")) {
            factMsg();
        } else if (msg.contains("!fuck you") && mentions.size() > 0) {
            fuckYouMsg();
        }
    }

    private boolean doesContainName(List<User> users, String name) {
        return users.stream().anyMatch(User -> User.getName().equals(name));
    }

    private void marcoMsg() {
        channel.sendMessage("Polo!").queue();
    }

    private void factMsg() {
        channel.sendMessage(factRetriever.getFact()).queue();
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
}
