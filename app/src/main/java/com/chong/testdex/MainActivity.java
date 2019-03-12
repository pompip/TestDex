package com.chong.testdex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chong.aidllibrary.Animal;
import com.chong.aidllibrary.MyPerson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.e("hi","hello");
                Bundle extras = intent.getExtras();
                if (extras==null)return;
                MyPerson myBean = extras.getParcelable("key");
                if (myBean==null)return;
                Toast.makeText(context, myBean.name, Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    MyReceiver receiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(receiver,new IntentFilter("hello"));

        loadDex();

        testRoot();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void testRoot(){
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: start ");
                RootManager.root(MainActivity.this);
                Log.i(TAG, "onClick: end ");
            }
        });
    }
    private String findBaseApk(){
        try {
            return getPackageManager().getPackageInfo("com.chong.testplugin",0).applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void loadDex(){
        String dexPath = findBaseApk();
        DexClassLoader dexClassLoader =
                new DexClassLoader(dexPath,
                        getCacheDir().getAbsolutePath(),null,getClassLoader());
        try {
            Class<?> someThingFactoryClass = dexClassLoader.loadClass("com.chong.testplugin.SomeThingFactory");
            Object someThingFactoryObj = someThingFactoryClass.newInstance();
            Object newInstance = someThingFactoryClass.getMethod("newInstance").invoke(someThingFactoryObj);
            Animal o = (Animal) newInstance;

            String eat = o.eat(1);

            Log.d(TAG, "loadDex: "+eat);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==1){
            Toast.makeText(this, "permmision is ok", Toast.LENGTH_SHORT).show();
        }
    }
}
