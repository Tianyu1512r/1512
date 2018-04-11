package com.example.zhoukao1.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhoukao1.Bean2;
import com.example.zhoukao1.R;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Fragment2 extends Fragment {

    private View view;
    private TextView tv2;
    private TextView tv1;
    private String path = "http://120.27.23.105/user/getDefaultAddr?uid=71";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment2, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        initData();
    }

    @SuppressLint("StaticFieldLeak")
    private void initData() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(path);
                try {
                    HttpResponse execute = client.execute(get);
                    int code = execute.getStatusLine().getStatusCode();
                    if (code == 200) {
                        InputStream content = execute.getEntity().getContent();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        byte[] arr = new byte[1024 * 10];
                        while ((len = content.read(arr)) != -1) {
                            baos.write(arr, 0, len);
                        }
                        return baos.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                Bean2 bean2 = gson.fromJson(s, Bean2.class);
                Bean2.DataBean data = bean2.getData();
                tv1.setText(data.getName());
                tv2.setText(data.getAddr());
            }
        }.execute(path);
    }
}
