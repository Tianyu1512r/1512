package com.example.test02_demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.xlistviewlibrary.View.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by WangZhiQiang on 2018/1/30.
 */

public class Fragment02 extends android.support.v4.app.Fragment implements XListView.IXListViewListener{

    private XListView listview;
    private ImageLoader instance;
    private MyAdapter adapter;
    String url = "http://api.expoon.com/AppNews/getNewsList/type/1/p/";
    int page;
    ArrayList<Goods.DataBean> list = new ArrayList<>();
    private View xlistview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment02, container, false);
        listview = view.findViewById(R.id.xlv);
        instance = ImageLoader.getInstance();
        initData(0);
        adapter = new MyAdapter();
        listview.setAdapter(adapter);
        listview.setPullLoadEnable(true);
        listview.setXListViewListener(this);
        return view;
    }

    private void initData(int page) {
        new MyAsynecTask().execute(url+page);
    }

    @Override
    public void onRefresh() {
        list.clear();
        initData(0);
    }

    @Override
    public void onLoadMore() {
         page++;
         initData(page);
    }

    private class MyAdapter extends BaseAdapter{

        private View view;
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
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            int viewType = getItemViewType(i);
                switch(viewType){
                        case 0:
                          ViewHolder1 holder1;
                          if (view == null){
                              holder1 = new ViewHolder1();
                              view = View.inflate(getActivity(), R.layout.list_item_00, null);
                              holder1.tv1 = view.findViewById(R.id.tv1);
                              holder1.tv2 = view.findViewById(R.id.tv2);
                              view.setTag(holder1);
                          }else {
                              holder1 = (ViewHolder1) view.getTag();
                          }
                          holder1.tv1.setText(list.get(i).getNews_id());
                          holder1.tv2.setText(list.get(i).getNews_title());
                        break;
                        case 1:
                             ViewHolder2 holder2;
                             if (view == null){
                                 holder2 = new ViewHolder2();
                                 view = View.inflate(getActivity(), R.layout.list_item_11, null);
                                 holder2.img_view = view.findViewById(R.id.imgview);
                                 holder2.text_view1 = view.findViewById(R.id.textview1);
                                 holder2.text_view2 = view.findViewById(R.id.textview2);
                                 view.setTag(holder2);
                             }else{
                                 holder2 = (ViewHolder2) view.getTag();
                             }
                             holder2.text_view1.setText(list.get(i).getNews_title());
                             holder2.text_view2.setText(list.get(i).getNews_id());
                             instance.displayImage(list.get(i).getPic_url(),holder2.img_view);
                        break;
                    }
            return view;
        }
        class ViewHolder1{
            private TextView tv1;
            private TextView tv2;
        }
        class ViewHolder2{
            private TextView text_view1;
            private TextView text_view2;
            private ImageView img_view;
        }
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
            Goods goods = gson.fromJson(s, Goods.class);
            List<Goods.DataBean> data = goods.getData();
            list.addAll(data);
            adapter.notifyDataSetChanged();
            uiComplete();
        }
    }
    private void uiComplete() {
       listview.stopLoadMore();
       listview.stopRefresh();
       listview.setRefreshTime("刚刚");
    }
}
