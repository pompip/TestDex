package com.chong.testplugin;

import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

public class SomeThing   {

    public void print(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public String genSome(){
        return "hello world";
    }
}
