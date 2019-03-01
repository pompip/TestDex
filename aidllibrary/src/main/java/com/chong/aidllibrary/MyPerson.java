package com.chong.aidllibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPerson implements Parcelable {

    public String name;
    public int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public MyPerson() {
    }

    protected MyPerson(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<MyPerson> CREATOR = new Creator<MyPerson>() {
        @Override
        public MyPerson createFromParcel(Parcel source) {
            return new MyPerson(source);
        }

        @Override
        public MyPerson[] newArray(int size) {
            return new MyPerson[size];
        }
    };


    @Override
    public String toString() {
        return "MyPerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
