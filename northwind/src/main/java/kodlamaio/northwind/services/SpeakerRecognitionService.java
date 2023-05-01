package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static kodlamaio.northwind.core.utilities.TextNormalizer.normalizeToTurkishChars;

@Service
public class SpeakerRecognitionService {
    public String enroll(String spk, MultipartFile file) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody fileBody = RequestBody.create(MediaType.parse("audio/x-wav"), file.getBytes());

        // Add the file to the MultipartBody.Builder instance
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "audio.wav", fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://api.4aithings.com/api/sv/enroll?spk="+spk )
                .method("POST", requestBody)
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        String tmp = null;

        try (Response response = client.newCall(request).execute()) {
            tmp = (response.body().string());
            response.body().close();
        }

        return tmp;
    }


    public String getSpeakers() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://spk:9081/getEnrolledSpeakers")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String tmp=(response.body().string());
        response.close();

        tmp=tmp.replace("\\u0131","ı");
        tmp=tmp.replace("\\u015f","ş");
        tmp=tmp.replace("\\u00e7","ç");
        tmp=tmp.replace("\\u011f","ğ");
        tmp=tmp.replace("\\u00fc","ü");
        tmp=tmp.replace("\\u00f6","ö");

        return tmp;
    }

    public String recognize(MultipartFile file) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody fileBody = RequestBody.create(MediaType.parse("audio/x-wav"), file.getBytes());

        // Add the file to the MultipartBody.Builder instance
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "audio.wav", fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://api.4aithings.com/api/sv/recognize" )
                .method("POST", requestBody)
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        String tmp = null;

        try (Response response = client.newCall(request).execute()) {
            tmp = (response.body().string());
            response.body().close();
        }

        return tmp;
    }

}
