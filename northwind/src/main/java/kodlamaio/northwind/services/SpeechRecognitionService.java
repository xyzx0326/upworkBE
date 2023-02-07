package kodlamaio.northwind.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class SpeechRecognitionService {

    public void init() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "model=49.nemo");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9080/initialize_model")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

    }

    public void initPunct() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "model=49.nemo");
        Request request = new Request.Builder()
                .url("http://192.168.1.50:9080/initialize_model")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

    }

    public void initAll() throws IOException {


        List<String> allmodels = new ArrayList();
        allmodels.add("49.nemo");
        allmodels.add("stt_hi_conformer_ctc_medium.nemo");
        allmodels.add("stt_de_conformer_ctc_large.nemo");
        allmodels.add("stt_en_conformer_ctc_large.nemo");
        allmodels.add("stt_enes_conformer_ctc_large.nemo");
        allmodels.add("stt_es_conformer_ctc_large.nemo");
        allmodels.add("stt_fr_conformer_ctc_large.nemo");
        allmodels.add("stt_ru_conformer_ctc_large.nemo");

        for(String modelSTT : allmodels) {
            OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String respModel="model="+modelSTT;
            RequestBody body = RequestBody.create(mediaType, respModel);
            Request request = new Request.Builder()
                    .url("http://192.168.1.50:9080/initialize_model")
                    .method("POST", body)
                    .addHeader("accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            response.close();
        }
    }

    public byte[] downloadYoutubeVideo(String link) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(180, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "inputText="+link);
        Request request = new Request.Builder()
                .url("http://192.168.1.50:7070/downloadYoutubeVideo")
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String tmp = (response.body().string());
        tmp = tmp.replace("\\u0131", "ı");
        tmp = tmp.replace("\"", "");
        tmp = tmp.replace("\n", "");
        tmp = tmp.replace("\\u015f", "ş");
        tmp = tmp.replace("\\u00e7", "ç");
        tmp = tmp.replace("\\u011f", "ğ");
        tmp = tmp.replace("\\u00fc", "ü");
        tmp = tmp.replace("\\u00f6", "ö");
        tmp = tmp.replace("\\u0130", "İ");
        tmp = tmp.replace("\\u015e", "Ş");
        tmp = tmp.replace("\\u00c7", "Ç");
        tmp = tmp.replace("\\u011e", "Ğ");
        tmp = tmp.replace("\\u00dc", "Ü");
        tmp = tmp.replace("\\u00d6", "Ö");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(tmp));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] mp4Data = out.toByteArray();

        return mp4Data;

//        String tmp = null;
//        tmp = (response.body().string());
//        tmp = tmp.replace("\\u0131", "ı");
//        tmp = tmp.replace("\\u015f", "ş");
//        tmp = tmp.replace("\\u00e7", "ç");
//        tmp = tmp.replace("\\u011f", "ğ");
//        tmp = tmp.replace("\\u00fc", "ü");
//        tmp = tmp.replace("\\u00f6", "ö");
//        return tmp;
    }
    public String recognize(String wavPath, String language) throws IOException {
        String modelName = null;



        if(language.equals("Arabic") ){
            Locale trLocale = new Locale("ar", "AE");
            Locale.setDefault(trLocale);
            OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(120, TimeUnit.SECONDS).build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "language="+language+"&inputText="+wavPath);
            Request request = new Request.Builder()
                    .url("http://192.168.1.50:7070/transcribeSeg")
                    .method("POST", body)
                    .addHeader("accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            String tmp = null;

            try (Response response = client.newCall(request).execute()) {
                tmp = (response.body().string());
//                if(language.equals("Turkish") ){
//                    tmp = tmp.replace("\\u0131", "ı");
//                    tmp = tmp.replace("\\u015f", "ş");
//                    tmp = tmp.replace("\\u00e7", "ç");
//                    tmp = tmp.replace("\\u011f", "ğ");
//                    tmp = tmp.replace("\\u00fc", "ü");
//                    tmp = tmp.replace("\\u00f6", "ö");
//                }
                response.body().close();
            }
            return tmp;
        }
        else if( language.equals("Turkish") || language.equals("English") || language.equals("Hindi") || language.equals("German") || language.equals("French") || language.equals("Russian") || language.equals("Spanish")){
            if(language.equals("Turkish")){
                Locale trLocale = new Locale("tr", "TR");
                Locale.setDefault(trLocale);
                modelName="49.nemo";
            } else if (language.equals("English")) {
                Locale trLocale = new Locale("en", "EN");
                Locale.setDefault(trLocale);
                modelName="stt_en_conformer_ctc_xlarge.nemo";
            }
            else if (language.equals("Hindi")) {
                Locale trLocale = new Locale("hi", "IN");
                Locale.setDefault(trLocale);
                modelName="stt_hi_conformer_ctc_medium.nemo";}
            else if (language.equals("German")) {
                Locale trLocale = new Locale("de", "DE");
                Locale.setDefault(trLocale);
                modelName="stt_de_conformer_ctc_large.nemo";
            } else if (language.equals("French")) {
                Locale trLocale = new Locale("fr", "FR");
                Locale.setDefault(trLocale);
                modelName="stt_fr_conformer_ctc_large.nemo";
            } else if (language.equals("Russian")) {
                modelName="stt_ru_conformer_ctc_large.nemo";
            } else if (language.equals("Spanish")) {
                Locale trLocale = new Locale("es", "ES");
                Locale.setDefault(trLocale);
                modelName="stt_es_conformer_ctc_large.nemo";
            } else if (language.equals("English-Spanish")) {
                Locale trLocale = new Locale("en", "EN");
                Locale.setDefault(trLocale);
                modelName="stt_enes_conformer_ctc_large.nemo";
            }
            String cont = "model=" + modelName + "&input speech=" + wavPath;
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, cont);
            Request request = new Request.Builder()
                    .url("http://192.168.1.50:9080/transcribeSeg")
                    .method("POST", body)
                    .addHeader("accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept-Encoding", "UTF-8")
                    .build();
            String tmp = null;
            try (Response response = client.newCall(request).execute()) {
                tmp = (response.body().string());
//                tmp = tmp.replace("\\u0131", "ı");
//                tmp = tmp.replace("\\u015f", "ş");
//                tmp = tmp.replace("\\u00e7", "ç");
//                tmp = tmp.replace("\\u011f", "ğ");
//                tmp = tmp.replace("\\u00fc", "ü");
//                tmp = tmp.replace("\\u00f6", "ö");
                response.body().close();
            }
            return tmp;
        }
        else{
            return "Language not supported";
        }
    }
}



//}
