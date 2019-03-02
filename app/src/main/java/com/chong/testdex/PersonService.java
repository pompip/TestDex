package com.chong.testdex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.chong.aidllibrary.IMyAidlInterface;
import com.chong.aidllibrary.MyPerson;

public class PersonService extends Service {

    class IMyA extends IMyAidlInterface.Stub {

        @Override
        public MyPerson getPerson()   {
            MyPerson person =  new MyPerson();
            person.age = 30;
            person.name = "kechong";
            return person;
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
