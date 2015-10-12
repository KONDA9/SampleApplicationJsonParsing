package com.altimetrik.sampleapplicationjsonparsing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bkondaiah on 09-10-2015.
 */


public class HTTPHandler implements Runnable {
    String s_url;
    Listener listen;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        connect();
    }

    // Getting URL
    public HTTPHandler(Listener listener, String url) {
        this.listen = listener;
        this.s_url = url;
    }


    // Connecting to URL
    public String connect() {

        HttpsURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String res = null;
        try {
            URL url = new URL(s_url);
            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            int ch;
            is = conn.getInputStream();

            while ((ch = is.read()) != -1) {

                bos.write(ch);
            }
            res = bos.toString();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
            listen.onError("Failure");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            conn.disconnect();
        }
        listen.onResponse(res, "success");
        return res;

    }

}
