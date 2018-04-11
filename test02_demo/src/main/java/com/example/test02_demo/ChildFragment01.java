package com.example.test02_demo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * author:Created by WangZhiQiang on 2018/1/30.
 */

public class ChildFragment01 extends android.support.v4.app.Fragment {


    private ListView list_view;
    private MyAdapter myAdapter;
    public String urlString = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    String urlBitmap = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    ArrayList<Bean.DataBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.childfragment01,null);
        list_view = view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myAdapter = new MyAdapter();
        list_view.setAdapter(myAdapter);
        new MyAsynecTack().execute(urlString);
    }

    private class MyAsynecTack extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String json = Util.getJson(strings[0]);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            list.addAll(bean.getData());
            myAdapter.notifyDataSetChanged();
        }
    }
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(getActivity(), R.layout.list_view, null);
            TextView text = view1.findViewById(R.id.text_view);
            ImageView img = view1.findViewById(R.id.img);
            text.setText(list.get(i).getNews_summary());
            new MyBitmapAsyTesk(img).execute(list.get(i).getPic_url());
            return view1;
        }
    }


    private class MyBitmapAsyTesk extends AsyncTask<String,Void,Bitmap>{
        ImageView img;

        public MyBitmapAsyTesk(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = Util.getBitmap(strings[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}
