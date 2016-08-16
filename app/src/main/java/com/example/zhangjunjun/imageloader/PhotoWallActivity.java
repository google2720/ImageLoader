package com.example.zhangjunjun.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import imageengine.DoubleCache;
import imageengine.ImageLoader;

public class PhotoWallActivity extends AppCompatActivity {

    public final static String[] imageThumbUrls = new String[]{
            "http://img-download.pchome.net/download/1k0/ma/3r/o73pp8-1daf.jpg",
            "http://img-download.pchome.net/download/1k0/ma/3r/o73pp8-q9a.jpg",
            "http://img-download.pchome.net/download/1k0/ma/3r/o73pp8-c6g.jpg",
            "http://img-download.pchome.net/download/1k0/ma/3r/o73pp9-1x8u.jpg",
            "http://img-download.pchome.net/download/1k0/mb/41/o75n9f-pqd.jpg",
            "http://img-download.pchome.net/download/1k0/mb/41/o75n9f-1fhm.jpg",
            "http://download.pchome.net/wallpaper/info-10672-3-1.html",
            "http://img-download.pchome.net/download/1k0/mn/3g/o7rp7o-9hw.jpg",

    };
    private android.widget.GridView photowall;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        this.photowall = (GridView) findViewById(R.id.photo_wall);
        imageLoader = new ImageLoader();
        imageLoader.setMemoryCache(new DoubleCache(this));

        photowall.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imageThumbUrls.length;
            }

            @Override
            public String getItem(int position) {
                return imageThumbUrls[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String url = getItem(position);
                View view;
                if (convertView == null) {
                    view = LayoutInflater.from(PhotoWallActivity.this).inflate(R.layout.photo_layout, null);
                } else {
                    view = convertView;
                }
                final ImageView imageView = (ImageView) view.findViewById(R.id.photo);
                // 给ImageView设置一个Tag，保证异步加载图片时不会乱序
                imageLoader.displayImage(PhotoWallActivity.this,url,imageView);
                return view;
            }
        });


    }
}
