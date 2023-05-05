package utils;

import okhttp3.*;

import java.io.IOException;

public class HttpUtils {

    public static String postMultipart(String url, String fileName, byte[] fileBytes) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody fileBody = RequestBody.create(fileBytes, MediaType.parse("audio/x-wav"));

        // Add the file to the MultipartBody.Builder instance
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, fileBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
