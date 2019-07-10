package com.chong.testdex.net;

public abstract class Callback<T> {
    public void cache(T cache){};

    public void success(T success){};

    public void fail(Exception err){};
}
