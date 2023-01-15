package kodlamaio.northwind.services;

import kodlamaio.northwind.core.utilities.responses.RecognizedSpeechResponse;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpeakerRecognitionService {
    public String enroll(String spk, String wavPath) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "speaker name="+spk+"&input speech="+wavPath);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9081/extractEmb")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String tmp=(response.body().string());

        return tmp;
    }

    public String init() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9081/initialize_model")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String tmp=(response.body().string());

        return tmp;
    }

    public String getSpeakers() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9081/getEnrolledSpeakers")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String tmp=(response.body().string());

        tmp=tmp.replace("\\u0131","ı");
        tmp=tmp.replace("\\u015f","ş");
        tmp=tmp.replace("\\u00e7","ç");
        tmp=tmp.replace("\\u011f","ğ");
        tmp=tmp.replace("\\u00fc","ü");
        tmp=tmp.replace("\\u00f6","ö");

        return tmp;
    }

    public String verify(String spk, String wavPath) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "speaker name="+spk+"&input speech="+wavPath);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9081/verify")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        String tmp=(response.body().string());

        return tmp;
    }

    public String recognize(String wavPath) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "input speech="+wavPath);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9081/recognize")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        String tmp=(response.body().string());

        tmp=tmp.replace("\\u0131","ı");
        tmp=tmp.replace("\\u015f","ş");
        tmp=tmp.replace("\\u00e7","ç");
        tmp=tmp.replace("\\u011f","ğ");
        tmp=tmp.replace("\\u00fc","ü");
        tmp=tmp.replace("\\u00f6","ö");


//        String tmp2 =tmp.split(":")[1];

        return tmp;
    }
}
