package dev.michael;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp {

    private int minusDateCount = 0;
    private final ObjectMapper mapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();
    private final LocalDateTime ldt = LocalDateTime.now();
    private LocalDateTime previousDate = ldt.minusDays(minusDateCount);

    private final Request request = new Request.Builder()
            .url("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")
            .build(); // defaults to GET
    private final Response response = client.newCall(request).execute();
    private final APOD apod = mapper.readValue(response.body().string(), APOD.class);


    public OkHttp() throws IOException {
    }

    public String getHdUrl() {
        return apod.hdUrl;
    }

    public String getExplanation() {
        return apod.explanation;
    }

    public String getTitle() {
        return apod.title;
    }

    public String getDate() {
        return apod.date;
    }

    public String getFormattedPrevDate(LocalDateTime date) {
        minusDateCount++;
        previousDate = date.minusDays(minusDateCount);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                .format(previousDate);
    }

    public String getFormattedNextDate(LocalDateTime date) {
        minusDateCount--;
        if (minusDateCount < 0) {
            System.out.println("You can't browse past today's date!");
            minusDateCount = 0;
        }
        else {
            previousDate = date.minusDays(minusDateCount);
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                .format(previousDate);
    }

    public String[] getPrevDateData() throws IOException {

        String dateRequest = "https://api.nasa.gov/planetary/apod?&api_key=DEMO_KEY&date=" + getFormattedPrevDate(ldt);
        Request request = new Request.Builder()
                .url(dateRequest)
                .build(); // defaults to GET
        Response response = client.newCall(request).execute();
        APOD apod = mapper.readValue(response.body().string(), APOD.class);
        String[] newData = new String[4];
        newData[0] = apod.hdUrl;
        newData[1] = apod.title;
        newData[2] = apod.explanation;
        newData[3] = apod.date;
        return newData;
    }

    public String[] getNextDateData() throws IOException {

        String dateRequest = "https://api.nasa.gov/planetary/apod?&api_key=DEMO_KEY&date=" + getFormattedNextDate(ldt);
        Request request = new Request.Builder()
                .url(dateRequest)
                .build(); // defaults to GET
        Response response = client.newCall(request).execute();
        APOD apod = mapper.readValue(response.body().string(), APOD.class);
        String[] newData = new String[4];
        newData[0] = apod.hdUrl;
        newData[1] = apod.title;
        newData[2] = apod.explanation;
        newData[3] = apod.date;
        return newData;
    }
}
