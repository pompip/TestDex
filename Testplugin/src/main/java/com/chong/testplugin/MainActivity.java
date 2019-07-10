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

import com.chong.aidllibrary.CallbackInterface;
import com.chong.aidllibrary.IMyAidlInterface;
import com.chong.aidllibrary.MyPerson;

public class MainActivity extends AppCompatActivity {
    //    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            if (msg.what == 1) {
//                Intent intent = new Intent();
//                intent.setAction("hello");
//                MyPerson myBean = new MyPerson();
//                myBean.age = 10;
//                myBean.name = "kechong";
//                intent.putExtra("key", myBean);
//                sendBroadcast(intent);
//                handler.sendEmptyMessageDelayed(1, 2000);
//            }
//            return false;
//        }
//    });
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
    CallbackInterface.Stub aidlCallback = new CallbackInterface.Stub() {
        @Override
        public void addPerson(MyPerson myperson) throws RemoteException {
            setText("addPerson: "+myperson.toString());
        }

        @Override
        public void addNum(int a, int b) throws RemoteException {
            setText("addNum: a:"+a + "b:"+b);
        }
    };


    IMyAidlInterface iMyAidlInterface;
    TextView textView;
    ScrollView scrollView;
 Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                return true;
            }
            return false;
        }
    });
    private void setText(String text) {
        textView.setText(textView.getText() + "\n" + text);
        handler.sendEmptyMessageDelayed(1,100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.date_contianer);
        scrollView = findViewById(R.id.scroll_view);


        Intent intent = new Intent("hello");
        intent.setPackage("com.chong.testdex");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iMyAidlInterface.registerCallback(aidlCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.dis_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iMyAidlInterface.unRegisterCallback(aidlCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
        findViewById(R.id.set_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPerson myPerson = new MyPerson();
                myPerson.name = "kechong 2";
                myPerson.age = 18;
                try {
                    iMyAidlInterface.setPerson(myPerson);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

//        handler.sendEmptyMessageDelayed(1, 2000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
