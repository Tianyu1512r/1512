package com.example.zhoukao1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhoukao1.Bean;
import com.example.zhoukao1.Bean1;
import com.example.zhoukao1.NetUtils;
import com.example.zhoukao1.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Fragment1 extends Fragment {

    private View view;
    private ListView lv;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Gson gson = new Gson();
            Bean bean = gson.fromJson(json, Bean.class);
            list = bean.getData();
            Myadapter1 myadapter1 = new Myadapter1();
            lv.setAdapter(myadapter1);
        }
    };
    private List<Bean.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment1, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = view.findViewById(R.id.lv);
        initData();
    }

    class Myadapter1 extends BaseAdapter {

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
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item, null);
                holder = new ViewHolder();
                holder.img = view.findViewById(R.id.img);
                holder.text_view1 = view.findViewById(R.id.text_view1);
                holder.text_view2 = view.findViewById(R.id.text_view2);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.text_view1.setText(list.get(i).getTitle());
            holder.text_view2.setText(list.get(i).getUserName());
            ImageLoader.getInstance().displayImage(list.get(i).getUserImg(), holder.img);
            return view;
        }

        class ViewHolder {
            ImageView img;
            TextView text_view1;
            TextView text_view2;
        }
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String json = NetUtils.getStr();
                Message message = new Message();
                message.obj = json;
                message.what = 0;
                handler.sendMessage(message);
            }
        }.start();
    }
}
