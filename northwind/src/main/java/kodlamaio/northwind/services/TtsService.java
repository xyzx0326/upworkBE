package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import static java.lang.System.in;

@Service
public class TtsService {

    public void initAll() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://images_tts_1:1111/initialize_all_model")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

    }


    public byte[] generateSpeech(String sentence, String language) throws IOException {
        String modelName=language;

        if(language.equals("Turkish")){
            sentence=sentence.replace(" ","%20");
            sentence = sentence.toLowerCase(new Locale("tr", "TR"));
            modelName="Turkish01";
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "ttsModel="+modelName+"&sentence="+sentence);
        Request request = new Request.Builder()
                .url("http://images_tts_1:1111/synthesize")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("/media/mesut/Depo1/tts.wav"));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] wavData = out.toByteArray();

        return wavData;
        } finally {
            response.body().close();
            in.close();
        }
    }
}
