package com.chong.testplugin;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chong.aidllibrary.IMyAidlInterface;
import com.chong.aidllibrary.MyPerson;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                Intent intent = new Intent();
                intent.setAction("hello");
                MyPerson myBean = new MyPerson();
                myBean.age = 10;
                myBean.name = "kechong";
                intent.putExtra("key", myBean);
                sendBroadcast(intent);
                handler.sendEmptyMessageDelayed(1, 2000);
            }
            return false;
        }
    });
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            setText("绑定");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            setText("解绑");
        }
    };

    IMyAidlInterface iMyAidlInterface;
    TextView textView;
    ScrollView scrollView;

    private void setText(String text) {

        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        textView.setText(textView.getText() + "\n" + text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.date_contianer);
        scrollView = findViewById(R.id.scroll_view);

        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("hello");
                intent.setPackage("com.chong.testdex");
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.dis_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });
        findViewById(R.id.get_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyPerson person = iMyAidlInterface.getPerson();
                    setText(person.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        handler.sendEmptyMessageDelayed(1, 2000);


    }
}
