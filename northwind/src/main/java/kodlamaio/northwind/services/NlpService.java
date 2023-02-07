package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NlpService {

    public void initParaphraser() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:1010/init")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }
    public void initTG() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:1010/initTG")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

    }

    public String runParaphraser(String sentence) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "sentence="+sentence);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:1010/parapharase")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String tmp=(response.body().string());

        return tmp;
    }

    public String runTG(String sentence) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "sentence="+sentence);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:1010/generateText")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String tmp=(response.body().string());

        return tmp;
    }

}
