package LAproject.aiap;

import LAproject.Configuration;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;
import java.io.IOException;


public class ArtificialInteligenceAPI {
    private static final String API_URL = Configuration.API_URL;
    private static final String API_KEY = Configuration.APIKey;

    public static String analyzeLawText(String lawText) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("model", Configuration.APIModel);

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", Configuration.APIMainPrompt + lawText);
        messages.add(message);

        json.add("messages", messages);

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
