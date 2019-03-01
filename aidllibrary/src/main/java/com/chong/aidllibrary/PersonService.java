package com.chong.aidllibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class PersonService extends Service {

    class IMyA extends IMyAidlInterface.Stub {


        @Override
        public MyPerson getPerson() throws RemoteException {
            return new MyPerson();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }
    public PersonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyA();
    }
}
