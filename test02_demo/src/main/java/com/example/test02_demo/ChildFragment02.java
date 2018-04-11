package com.example.test02_demo;

import android.annotation.SuppressLint;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * author:Created by WangZhiQiang on 2018/1/30.
 */

public class ChildFragment02 extends android.support.v4.app.Fragment {

    private ListView list_item;
    private MyAdapter adapter;
    public String urlString = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    String urlBitmap = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    ArrayList<Bean.DataBean> list = new ArrayList<>();
    private ImageLoader imageLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.childfragment02, null);
        list_item = view.findViewById(R.id.list_item);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MyAdapter();
        list_item.setAdapter(adapter);
        imageLoader = ImageLoader.getInstance();
        new MyAsynecTask().execute(urlString);
    }

    private class MyAsynecTask extends AsyncTask<String,Void,String>{
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
            adapter.notifyDataSetChanged();
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

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(getActivity(), R.layout.list_item, null);
            ImageView img_view = view1.findViewById(R.id.img_view);
            TextView text_name = view1.findViewById(R.id.text_name);
            text_name.setText(list.get(i).getNews_title());
            DisplayImageOptions options = ImageLoaderUtil.getOption();
           imageLoader.displayImage(urlBitmap,img_view,options);
            return view1;
        }
    }

    
}
