package com.chong.testplugin;

import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

import com.chong.aidllibrary.Animal;

public class SomeThing   implements Animal {

    public void print(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public String genSome(){
        return "hello world";
    }

    @Override
    public String eat(int what) {
        return "eat"+ what*2;
    }
}
