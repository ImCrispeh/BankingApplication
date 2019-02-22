import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;
import api.KeyLoader;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = new KeyLoader().loadKey("JDA_TOKEN");
        JDA jda = new JDABuilder(token)
                .addEventListener(new Main())
                .build();
        jda.awaitReady();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();

        if (!author.isBot()) {
            ChannelMessageHandler channelMessageHandler = new ChannelMessageHandler(event);
            channelMessageHandler.handleMsg();
        }
    }
}
