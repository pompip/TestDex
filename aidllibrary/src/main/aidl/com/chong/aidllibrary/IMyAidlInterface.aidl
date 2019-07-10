// IMyAidlInterface.aidl
package com.chong.aidllibrary;
import com.chong.aidllibrary.MyPerson;
import com.chong.aidllibrary.CallbackInterface;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    MyPerson getPerson();
    void setPerson(in MyPerson myPerson);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void registerCallback(CallbackInterface callback);
    void unRegisterCallback(CallbackInterface callback);
}
