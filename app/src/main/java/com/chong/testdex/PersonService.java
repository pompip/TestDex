package com.chong.testdex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.chong.aidllibrary.CallbackInterface;
import com.chong.aidllibrary.IMyAidlInterface;
import com.chong.aidllibrary.MyPerson;

public class PersonService extends Service {
    private static final String TAG = "chong";

    RemoteCallbackList<CallbackInterface> remoteCallbackList = new RemoteCallbackList<>();

    class IMyA extends IMyAidlInterface.Stub {

        @Override
        public MyPerson getPerson() throws RemoteException {
            MyPerson person = new MyPerson();
            person.age = 30;
            person.name = "kechong";

            int i = remoteCallbackList.beginBroadcast();
            while (i > 0) {
                i--;
                remoteCallbackList.getBroadcastItem(i).addNum(i, i);
            }
            remoteCallbackList.finishBroadcast();

            return person;
        }

        @Override
        public void setPerson(MyPerson myPerson) throws RemoteException {
            Log.d(TAG, "setPerson() called with: myPerson = [" + myPerson + "]");


            int i = remoteCallbackList.beginBroadcast();
            while (i > 0) {
                i--;
                myPerson.name = myPerson.name + " callback" +i;
                remoteCallbackList.getBroadcastItem(i).addPerson(myPerson);
            }
            remoteCallbackList.finishBroadcast();

        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {


        }

        @Override
        public void registerCallback(CallbackInterface callback) throws RemoteException {

            remoteCallbackList.register(callback);

        }

        @Override
        public void unRegisterCallback(CallbackInterface callback) throws RemoteException {

            remoteCallbackList.unregister(callback);
        }
    }

    public PersonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyA();
    }
}
