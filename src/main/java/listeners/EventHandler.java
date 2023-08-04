package listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class EventHandler extends ListenerAdapter implements IntFunction<Object> {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final String openAIKey = Dotenv.load().get("OPENAI_KEY");
    private final String endpoint = "https://api.openai.com/v1/engines/davinci/completions";
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if(event.isFromGuild()) {
            System.out.println("The message was received in a guild.");
        } else if(event.isFromType(ChannelType.PRIVATE)) {
            System.out.println("The message was received in a private channel.");
        }
        String user = event.getAuthor().toString();
        String message = event.getMessage().getContentDisplay();

        System.out.println("Event received from " + user);
        System.out.println("Message content: " + message);

        if (message.startsWith("!ask ")) {
            try {
                String question = message.substring(5);
                System.out.println("Question received: " + question);

                String gptResponse = chatGptResponse(question);
                String discMessage = gptResponse;

                event.getChannel().sendMessage(discMessage).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object apply(int value) {
        return null;
    }

    public String chatGptResponse(String prompt) throws IOException {
        String json = "{" +
                "\"model\": \"text-davinci-003\"," +
                "\"temperature\": 0.6," +
                "\"max_tokens\": 256," +
                "\"prompt\": \"" + prompt + "\"" +
                "}";

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions") // changed endpoint
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openAIKey)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        if (jsonResponse.has("error")) {
            System.out.println("OpenAI API error: " + jsonResponse.get("error").getAsJsonObject());
            // Handle the error response as needed
            return "An error occurred while processing your request.";
        } else {
            return jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();
        }
    }
}
