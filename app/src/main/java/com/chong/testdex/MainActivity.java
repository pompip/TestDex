package com.chong.testdex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chong.aidllibrary.Animal;
import com.chong.aidllibrary.MyPerson;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.jaredrummler.android.processes.models.Statm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.e("hi", "hello");
                Bundle extras = intent.getExtras();
                if (extras == null) return;
                MyPerson myBean = extras.getParcelable("key");
                if (myBean == null) return;
                Toast.makeText(context, myBean.name, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    MyReceiver receiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(receiver, new IntentFilter("hello"));
        testRoot();
        initCmdOutput();
        initWifiAdbButton();
        try {
            androidShell();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    TextView cmd_output;
    ExecutorService executor;

    private void initCmdOutput() {
        cmd_output = findViewById(R.id.cmd_output);
        executor = Executors.newSingleThreadExecutor();
    }

    private void initWifiAdbButton() {
        findViewById(R.id.wifi_adb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exeWifiAdb();
            }
        });
    }

    private void exeWifiAdb() {
//        String[] cmds = {
//                "su setprop service.adb.tcp.port 5555",
//                "/sbin/su stop adbd",
//                "/sbin/su start adbd"
//        };
//        CMD.execCommand(cmds,true);

        ProcessBuilder builder = new ProcessBuilder("su");
        try {
            final Process process = builder.start();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " );
                    InputStream inputStream = process.getInputStream();
                    BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
                    while (true){
                        try {
                            final String line = bufferedReader.readLine();
                            Log.e(TAG, "setText: "+line );
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cmd_output.setText(line+"\n");
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
            OutputStream outputStream = process.getOutputStream();
            outputStream.write("setprop service.adb.tcp.port 5555\n".getBytes());
            outputStream.flush();
            outputStream.write("stop adbd\n".getBytes());
            outputStream.flush();
            outputStream.write("start adbd\n".getBytes());
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void androidShell() throws IOException {
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

        for (AndroidAppProcess process : processes) {
            // Get some information about the process
            String processName = process.name;


            Stat stat = process.stat();
            int pid = stat.getPid();
            int parentProcessId = stat.ppid();
            long startTime = stat.stime();
            int policy = stat.policy();
            char state = stat.state();

            Statm statm = process.statm();
            long totalSizeOfProcess = statm.getSize();
            long residentSetSize = statm.getResidentSetSize();

            Log.e(TAG, "androidShell: " + pid + " shell name:" + processName);

//            PackageInfo packageInfo = process.getPackageInfo(context, 0);
//            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
        }
    }

    private void exeShell(int pid) {
        try {
            String command = "cat /proc/" + pid + "/stat\n";
            Process exec = Runtime.getRuntime().exec(command);
            OutputStream outputStream = exec.getOutputStream();
//

            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            outputStream.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                Log.e(TAG, "exeShell: " + line);
                result.append(line);
            }
            exec.waitFor();
            bufferedReader.close();
            exec.destroy();
            Log.e(TAG, "exeShell: " + "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getAppCpuTime(int pid) throws Exception { // 获取应用占用的CPU时间
        long appCpuTime = 0;
        try {
            String[] cpuInfos = null;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + pid + "/stat")), 1000);
            CMD.CommandResult commandResult = CMD.execCommand("cat /prop/" + pid + "/stat", false);
            String successMsg = commandResult.successMsg;
//            String load = reader.readLine();
            cpuInfos = successMsg.split(" ");

            //20170731 参考源码15,16两索引的数据不用来计算的CPU值 frameworks/base/core/java/com/android/internal/os/ProcessCpuTracker
//            appCpuTime = Long.parseLong(cpuInfos[13])
//                    + Long.parseLong(cpuInfos[14])
//                    + Long.parseLong(cpuInfos[15])
//                    + Long.parseLong(cpuInfos[16]);
            Log.e(TAG, "getAppCpuTime: " + successMsg);
//            reader.close();
        } catch (Exception e) {
            Log.e(TAG, pid + " ------------getAppCpuTime Exception ." + e.getMessage());
            throw e;
        }
        return appCpuTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void testRoot() {
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: start ");
                RootManager.root(MainActivity.this);
                Log.i(TAG, "onClick: end ");
            }
        });
    }

    private String findBaseApk() {
        try {
            return getPackageManager().getPackageInfo("com.chong.testplugin", 0).applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void loadDex() {
        String dexPath = findBaseApk();
        DexClassLoader dexClassLoader =
                new DexClassLoader(dexPath,
                        getCacheDir().getAbsolutePath(), null, getClassLoader());
        try {
            Class<?> someThingFactoryClass = dexClassLoader.loadClass("com.chong.testplugin.SomeThingFactory");
            Object someThingFactoryObj = someThingFactoryClass.newInstance();
            Object newInstance = someThingFactoryClass.getMethod("newInstance").invoke(someThingFactoryObj);
            Animal o = (Animal) newInstance;

            String eat = o.eat(1);

            Log.d(TAG, "loadDex: " + eat);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Toast.makeText(this, "permmision is ok", Toast.LENGTH_SHORT).show();
        }
    }

    public void testMediaPlayer() {

    }
}
