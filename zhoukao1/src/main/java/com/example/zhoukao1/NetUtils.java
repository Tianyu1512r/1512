package com.example.zhoukao1;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/14.
 */

public class NetUtils {
    public static String getStr() {

        String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?pageSize=10&page=1";

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();

                int len;
                byte[] arr = new byte[1024 * 20];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ((len = inputStream.read(arr)) != -1) {
                    baos.write(arr, 0, len);
                }

                return baos.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
