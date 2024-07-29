package LAproject.aiap;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckQuota {
    private static final String API_URL = "https://api.openai.com/v1/usage";
    private static final String API_KEY = "chang";

    public static String checkQuota() throws IOException {
        OkHttpClient client = new OkHttpClient();

        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ISO_LOCAL_DATE);

        HttpUrl url = HttpUrl.parse(API_URL).newBuilder()
                .addQueryParameter("date", date)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
