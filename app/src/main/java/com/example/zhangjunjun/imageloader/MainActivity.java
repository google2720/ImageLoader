package com.example.zhangjunjun.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import imageengine.DoubleCache;
import imageengine.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private android.widget.ImageView ivtest;
    private ImageLoader imageLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ivtest = (ImageView) findViewById(R.id.iv_test);
        imageLoader = new ImageLoader();
        imageLoader.setMemoryCache(new DoubleCache(this));
    }



    public void clear(View v) {
        ivtest.setImageResource(R.mipmap.ic_launcher);
    }

    public void show(View v) {
        imageLoader.displayImage(this,"http://www.sinaimg.cn/dy/slidenews/4_img/2012_33/704_723615_122828.jpg",ivtest);
    }


}
