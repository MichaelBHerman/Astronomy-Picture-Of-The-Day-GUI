package dev.michael;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class OKHttp {

    public int minusDateCount = 1;
    final ObjectMapper mapper = new ObjectMapper();
    public OkHttpClient client = new OkHttpClient();
    public LocalDateTime ldt = LocalDateTime.now();
    public LocalDateTime previousDate = ldt.minusDays(minusDateCount);


    Request request = new Request.Builder()
            .url("https://api.nasa.gov/planetary/apod?api_key=Emii5aEQxDLySfoUKSX1XKXTBNiPp5bMXCdeXr5c")
            .build(); // defaults to GET
    Response response = client.newCall(request).execute();
    APOD apod = mapper.readValue(response.body().string(), APOD.class);


    public OKHttp() throws IOException {
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
        previousDate = date.minusDays(minusDateCount);
        minusDateCount++;
        return DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                .format(previousDate);
    }

    public String getFormattedNextDate(LocalDateTime date) {
        minusDateCount--;
        previousDate = date.minusDays(minusDateCount);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                .format(previousDate);
    }
    //TODO: refactor this so that it concatenates the correct method depending on next or previous button press
    public String[] getPrevDateData() throws IOException {

        String dateRequest = "https://api.nasa.gov/planetary/apod?&api_key=Emii5aEQxDLySfoUKSX1XKXTBNiPp5bMXCdeXr5c&date=" + getFormattedPrevDate(ldt);
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

        String dateRequest = "https://api.nasa.gov/planetary/apod?&api_key=Emii5aEQxDLySfoUKSX1XKXTBNiPp5bMXCdeXr5c&date=" + getFormattedNextDate(ldt);
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
