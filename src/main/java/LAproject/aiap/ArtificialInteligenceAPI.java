package LAproject.aiap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class ArtificialInteligenceAPI {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "sk-proj-09lEsvtuz13yJpfnzzAAT3BlbkFJ4Vdg2QiKCalAodrO7ikT";

    public static String analyzeLawText(String lawText) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-4o");

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", "Analyze the following law text and extract all construction parameters." +
                "Reading an law text you have to understand the hierarchy of the titles and subtitle. " +
                "Ensure that numerical values and boolean characteristics are correctly identified. " +
                "Boolean values should be associated with clear characteristics," +
                " for example: 'Permitido construção com contraplacado marítimo (true/false)'. " +
                "The response should be in english JSON format with an array of objects, " +
                "each containing 'property_name', 'value', and 'value_type' (either 'boolean' or 'double'). " +
                "Example: [{\"property_name\": \"Maximum number of floors for construction\", " +
                "\"value\": 4, \"value_type\": \"double\"}, {\"property_name\": \"Allowed construction with certain type of material\", " +
                "\"value\": true, \"value_type\": \"boolean\"}] \n\n" + lawText);
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
