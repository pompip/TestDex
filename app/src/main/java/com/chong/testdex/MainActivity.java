package com.chong.testdex;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chong.aidllibrary.MyPerson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.e("hi","hello");

                Bundle extras = intent.getExtras();

                MyPerson myBean = extras.getParcelable("key");
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



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void loadDex(){
        DexClassLoader dexClassLoader =
                new DexClassLoader(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Testplugin-debug.apk",
                        getCacheDir().getAbsolutePath(),null,getClassLoader());
        try {
            Class<?> ClassSomeThing = dexClassLoader.loadClass("com.chong.testplugin.SomeThing");
            Object o = ClassSomeThing.newInstance();
            Method genSome = ClassSomeThing.getMethod("genSome");
            String invoke = (String) genSome.invoke(o);

            Method print = ClassSomeThing.getMethod("print", Context.class, String.class);
            print.invoke(o,this,"hello bonree");
            Toast.makeText(this, invoke, Toast.LENGTH_SHORT).show();
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
