package com.chong.testdex;

import android.os.Parcel;
import android.os.Parcelable;

public class MyBean implements Parcelable {

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

    public MyBean() {
    }

    protected MyBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<MyBean> CREATOR = new Creator<MyBean>() {
        @Override
        public MyBean createFromParcel(Parcel source) {
            return new MyBean(source);
        }

        @Override
        public MyBean[] newArray(int size) {
            return new MyBean[size];
        }
    };
}
