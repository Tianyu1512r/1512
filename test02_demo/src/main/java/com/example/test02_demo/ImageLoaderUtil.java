package com.example.test02_demo;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * author:Created by WangZhiQiang on 2018/1/30.
 */

class ImageLoaderUtil {

    public static DisplayImageOptions getOption() {
       DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
               .showImageOnFail(R.mipmap.ic_launcher)  //配置默认图******
                .showImageOnLoading(R.mipmap.ic_launcher)//配置默认图******
                .showImageForEmptyUri(R.mipmap.ic_launcher)//配置默认图******
                .bitmapConfig(Bitmap.Config.ARGB_8888)//配置图片解码格式,图片比较清晰 *****
                .displayer(new RoundedBitmapDisplayer(15)) //配置圆角图片   *******
                .cacheInMemory(true)                    //是否缓存到内存  ******
                .cacheOnDisk(true)                      //是否缓存到sd卡  ******
                .build();
        return imageOptions;
    };
}
