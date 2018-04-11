package com.example.mrt.tianyu18129week1;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrt.tianyu18129week1.NetUtil.NetUtil;
import com.example.mrt.tianyu18129week1.Product.Product;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    public String urlString = "http://api.iclient.ifeng.com/ClientNews?id=JS83,FOCUSJS83&action=defalult";
    String urlBitmap = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    private ListView listView;
    ArrayList<Product.DataBean> dataBeanList = new ArrayList();// 集合为空；
    private MAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        //直接调用asyncTask类请求网络,把url传进去
        mAdapter = new MAdapter();
        listView.setAdapter(mAdapter);

        new MAsyncTask().execute(urlString);

    }


    class MAsyncTask extends AsyncTask<String,Void,String> {

        /**
         * 这个方法运行在子线程;让他去请求网络,
         * @param strings  可变参数;
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            String netJson = NetUtil.getNetJson(stringUrl);
            return netJson;
        }

        @Override
        protected void onPostExecute(String netJson) {
            super.onPostExecute(netJson);
            Gson gson = new Gson();
            Product product = gson.fromJson(netJson, Product.class);
            dataBeanList.addAll(product.getData());
            mAdapter.notifyDataSetChanged();//可以直接更新适配器,因为这个方法是在主线程

        }
    }





    private class MAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            Log.e("wzq", "getCount:" + dataBeanList.size());
            return dataBeanList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataBeanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(MainActivity.this, R.layout.item02, null);
            TextView tv_name = view1.findViewById(R.id.tv_name);
            ImageView iv = view1.findViewById(R.id.iv);
            tv_name.setText(dataBeanList.get(i).getNews_summary());

            new MBitmapAsyncTask(iv).execute(dataBeanList.get(i).getPic_url());

            Log.e("wzq", "getCount:" + "执行getview");
            return view1;
        }
    }


    /**
     * 用asyncTask请求服务器图片
     */
    private class MBitmapAsyncTask extends AsyncTask<String,Void,Bitmap> {

        ImageView iv;

        public MBitmapAsyncTask(ImageView iv) {
            this.iv = iv;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap mbitmap = NetUtil.getNetBitmap(strings[0]);

            return mbitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //大胆的更新ui,因为这是主线程
            iv.setImageBitmap(bitmap);

        }
    }
}

