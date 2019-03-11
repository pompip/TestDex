package com.chong.testdex;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class RootManager {

    public static void root(Context context){
        CMD.CommandResult commandResult = CMD.execCommand("chmod 777 " + context.getPackageCodePath(), true);
        Log.i("RootManager ","result:"+"start");
        Log.i("RootManager ","result:"+commandResult.result);
        Log.i("RootManager ","error:"+commandResult.errorMsg);
        Log.i("RootManager ","success:"+commandResult.successMsg);
    }

    public static void getPack(Context context){
//        context.getPackageManager().getPackageInfo().applicationInfo.
    }
}
