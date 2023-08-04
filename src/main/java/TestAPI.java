import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestAPI {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        OkHttpClient client = new OkHttpClient();
        String json = "{" +
                "\"prompt\": \"Translate the following English text to French: '{\\\"text\\\": \\\"Hello, world!\\\"}'\"," +
                "\"max_tokens\": 60" +
                "}";

        RequestBody body = RequestBody.create(
                json,
                okhttp3.MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/engines/text-davinci-002/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + dotenv.get("OPENAI_KEY"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
