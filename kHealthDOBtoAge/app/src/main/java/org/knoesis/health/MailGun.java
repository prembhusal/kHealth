package org.knoesis.health;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PA_VSridha on 2/26/2017.
 */

public class MailGun extends Thread {

    private String TO_ADDRESS;
    private String CC_ADDRESSES;
    private String MESSAGE;
    private String SUBJECT;
    private String FROM_ADDRESS = "khealthuser@gmail.com";

    public MailGun(String TO_ADDRESS, String CC_ADDRESSES, String MESSAGE, String SUBJECT) {
        this.TO_ADDRESS = TO_ADDRESS;
        this.CC_ADDRESSES = CC_ADDRESSES;
//        for (int i = 0; i < CC_ADDRESSES.length; i++) {
//            this.CC_ADDRESSES[i] = CC_ADDRESSES[i];
//        }
        this.MESSAGE = MESSAGE;
        this.SUBJECT = SUBJECT;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "from=" + this.FROM_ADDRESS + "&to=" + this.TO_ADDRESS + "&text=" + this.MESSAGE + "&subject=" + this.SUBJECT + "&cc=" + this.CC_ADDRESSES);
        Request request = new Request.Builder()
                .url("https://api.mailgun.net/v3/sandbox41efd47407b94f819becb4ae3867be94.mailgun.org/messages")
                .post(body)
                .addHeader("authorization", "Basic YXBpOmtleS05ODJjNzM2ZTBkOTU4ZGVjMzI0ZGYxYjhkMmZhZWJkNw==")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "4b43e27e-a35e-921f-9a14-c31bce938382")
                .build();
        Log.w("MailGun", "Making the api call");
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {

            System.out.println("Error while sending email via MAIL Gun");
            Log.e("Error I/O : MailGun", " Error while sending email via MAIL Gun");
        }

        Log.w("MailGun", "Logging the response...");

        try {
            Log.d("response", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.close();


    }
}
