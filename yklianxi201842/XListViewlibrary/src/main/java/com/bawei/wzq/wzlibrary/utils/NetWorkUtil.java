package com.bawei.wzq.wzlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author:Created by WangZhiQiang on 2017-08-30.
 * NetWorkUtil是我们自己封装的请求网络工具类
 * 可以请求json字符串并返回
 * 可以请求图片并返回
 */

public class NetWorkUtil {

    private int responseCode;

    /**
     * 获取图片并返回；
     * @param urlString
     * @return
     */
    public Bitmap getNetImage(String urlString){
        try {
            //得到URL对象,这个对象可以打开连接
            URL url = new URL(urlString);
            //打开连接,HttpURLConnection:是URLConnection的子类;子类的方法比较多;都用子类
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //服务器返回的状态码

            int responseCode = urlConnection.getResponseCode();
            //请求成功
            if (responseCode == 200){
                InputStream inputStream = urlConnection.getInputStream();
                //BitmapFactory类封装了好多关于图片处理的方法;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return  bitmap;

            }else {
                Log.e("skn请求图片","responseCode:"+ responseCode);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 请求json字符串，
     * @param urlString url
     * @return   json字符串
     */
    public String getNetString(String urlString){

        try {
            //得到URL对象,这个对象可以打开连接
            URL url = new URL(urlString);
            //打开连接,HttpURLConnection:是URLConnection的子类;子类的方法比较多;都用子类
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //服务器返回的状态码

            int responseCode = urlConnection.getResponseCode();
            //请求成功
            if (responseCode == 200){
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String stringTemp = "";
                while ((stringTemp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(stringTemp);
                }
                String result = stringBuilder.toString();
                Log.e("skn请求json", "result:" + result);
                return result;
            }else {
                Log.e("skn","responseCode:"+ responseCode);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
