package com.example.zhangjunjun.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private android.widget.ImageView ivtest;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ivtest = (ImageView) findViewById(R.id.iv_test);
        imageLoader = new ImageLoader();

        imageLoader.displayImage("http://i1.hoopchina.com.cn/blogfile/201608/09/BbsImg147072721085318_480x320.jpg",ivtest);

    }
}
