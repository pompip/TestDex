package com.chong.testdex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chong.testdex.net.Callback;
import com.chong.testdex.net.Nets;

public class LruCacheActivity extends AppCompatActivity {
    private static final String TAG = "LruCacheActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conncet();
            }
        });
    }

    void conncet(){
        Nets.url("http://java.asuscomm.com:9000/api/article/all").get().success(new Callback() {
            @Override
            public void success(Object success) {
                Log.d(TAG, "success: "+Thread.currentThread());
            }
        }).cache(new Callback() {
            @Override
            public void cache(Object cache) {
                Log.d(TAG, "cache: "+Thread.currentThread());
            }
        }).fail(new Callback() {
            @Override
            public void fail(Exception err) {
                Log.d(TAG, "fail: "+Thread.currentThread());

            }
        });
    }



}
