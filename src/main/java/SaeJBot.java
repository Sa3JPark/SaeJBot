import io.github.cdimascio.dotenv.Dotenv;
import listeners.EventHandler;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class SaeJBot {

    private Dotenv config;
    private ShardManager shardManager;

    public SaeJBot() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Chatting with Users!"));
        builder.setEnableShutdownHook(true);
        builder.setBulkDeleteSplittingEnabled(false);
        shardManager = builder.build();
        shardManager.addEventListener(new EventHandler()); // Add EventHandler listener directly here
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main (String[] args) {
        try {
            SaeJBot bot = new SaeJBot();
        } catch (LoginException a) {
            System.out.println("This input token is invalid!");
        }
    }
}
