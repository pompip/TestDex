package com.chong.testdex.net;

import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Nets {
    private Nets() {

    }

    public static Builder url(String url) {
        return new Builder().url(url);
    }

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private ExecutorService queuePool = Executors.newSingleThreadExecutor();
    private Queue<Builder> requestQueue = new LinkedList<>();
    private LruCache<String, Builder> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 8));
    private Handler handler = new Handler(Looper.getMainLooper());
    {

    }

    public static class Builder {
        private String method = "GET";
        private String url = "";
        private Callback successCallback;
        private Callback failCallback;
        private Callback cacheCallback;
        private HashMap<String, Object> params = new HashMap<>();
        private Object result;

        private static Nets nets = new Nets();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            method = "GET";
            return this;
        }

        public Builder post() {
            method = "POST";
            return this;
        }

        public Builder param(Map<String, Object> map) {
            params.putAll(map);
            return this;
        }

        public Builder param(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public Builder success(Callback callback) {
            this.successCallback = callback;
            nets.requestQueue.offer(this);
            nets.connect();
            return this;
        }

        public Builder fail(Callback callback) {
            this.failCallback = callback;
            return this;
        }

        public Builder cache(Callback callback) {
            this.cacheCallback = callback;
            return this;
        }
        void cache(Object result) {
            nets.cache.put(url, this);
            this.result = result;
        }

    }


    public void connect() {//todo has bug.queue does not work;only need one thread poll queue
        queuePool.submit(new Runnable() {
            @Override
            public void run() {
                Builder builder;
                while ((builder = requestQueue.poll()) != null) {
                    final Builder cache = Nets.this.cache.get(builder.url);
                    final Callback cacheCallback = builder.cacheCallback;
                    if (cacheCallback !=null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                cacheCallback.cache(cache==null?null:cache.result);
                            }
                        });
                    }

                    threadPool.submit(new NetRunnable(builder));
                }
            }
        });
    }


    class NetRunnable implements Runnable {
        Builder builder;

        public NetRunnable(Builder builder) {
            this.builder = builder;
        }

        @Override
        public void run() {
            try {

                final Object o = parseByte(parseInputStream(connectGet(builder.url, builder.method)));
                builder.cache(o);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.successCallback.success(o);
                    }
                });

            } catch (final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.failCallback.fail(e);
                    }
                });

                e.printStackTrace();
            }
        }
    }


    private InputStream connectGet(String url, String method) throws IOException {
        int timeout = 3000;
        System.out.println("url:" + url);
        System.out.println("method:" + method);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeout);
        connection.connect();
        return connection.getInputStream();


    }

    private byte[] parseInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        int len = 1024;
        byte[] temp = new byte[len];
        int i = -1;
        while ((i = bis.read(temp, 0, len)) > 0) {
            baos.write(temp, 0, i);
        }
        return baos.toByteArray();
    }

    private Object parseByte(byte[] bytes) {
        JSONArray objects = JSON.parseArray(new String(bytes));
//        System.out.println(objects);
        return objects;
    }


}
