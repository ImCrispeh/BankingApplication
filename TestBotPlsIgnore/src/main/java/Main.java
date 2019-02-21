import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = "NTQ3NjM5NzY0MzgwMjg2OTkz.D05tAQ.2o8zDSBBTzL1uer_iMY1Z_FPVIY";
        JDA jda = new JDABuilder(token)
                .addEventListener(new Main())
                .build();
        jda.awaitReady();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String msg = event.getMessage().getContentRaw().toLowerCase();
        MessageChannel channel = event.getChannel();
        List<User> mentions = event.getMessage().getMentionedUsers();

        if (!author.isBot()) {
            ChannelMessageHandler channelMessageHandler = new ChannelMessageHandler(msg, channel, author, mentions);
            channelMessageHandler.handleMsg();
        }
    }
}
