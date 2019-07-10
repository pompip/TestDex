// CallbackInterface.aidl
package com.chong.aidllibrary;

// Declare any non-default types here with import statements
import com.chong.aidllibrary.MyPerson;
interface CallbackInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addPerson(in MyPerson myperson);
    void addNum(int a,int b);
}
