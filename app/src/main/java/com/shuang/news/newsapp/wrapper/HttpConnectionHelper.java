package com.shuang.news.newsapp.wrapper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpConnectionHelper {

    private static final int CONNECT_TIME = 10000;

    public static void get(final String path, final HttpCallback<String> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getInThread(path, new MyHandler<>(callback));
            }
        }).start();
    }

    private static String getInThread(String path, Handler handler) {
        String result = "";
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);

            LogUtil.i("http url: " + path);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);
            conn.connect();

            inputStream = conn.getInputStream();
            byte[] buffer = new byte[4096];
            StringBuilder builder = new StringBuilder();
            long currentTime = System.currentTimeMillis();
            //处理第一次连接时超时时间
            boolean isFirst = true;
            /*
                isFirst && !(isFirst = isConnectTimeout(currentTime))
                第一次连接，并且 连接未超时
             */
            while (inputStream.read(buffer) != -1 || (isFirst && !isConnectTimeout(currentTime))) {
                String s = new String(buffer, Charset.forName("utf-8"));
                builder.append(s);
                //第一次连接成功后设置为false，否则连接结束时无法判断
                if (!TextUtils.isEmpty(s)) {
                    isFirst = false;
                }
            }
            result = builder.toString();

            LogUtil.i("http result: " + result);

            Message obtain = Message.obtain();
            obtain.obj = result;
            obtain.arg1 = MyHandler.CODE_OK;
            handler.sendMessage(obtain);
            inputStream.close();
            conn.disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i("http error: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null)
                conn.disconnect();

        }
        LogUtil.i("http error: None");
        handler.sendEmptyMessage(MyHandler.CODE_NO);
        return result;
    }

    /**
     * 是否连接超时
     * @param startTime 开始时间
     * @return 是否超时
     */
    private static boolean isConnectTimeout(long startTime){
        return System.currentTimeMillis() - startTime > CONNECT_TIME;
    }

    private static class MyHandler<T> extends Handler {

        private static final int CODE_OK = 200;
        private static final int CODE_NO = -1;
        private HttpCallback<T> mCallback;

        MyHandler(HttpCallback<T> callback) {
            super(Looper.getMainLooper());
            mCallback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mCallback != null) {
                if (msg.arg1 == CODE_OK) {
                    mCallback.success((T) msg.obj);
                } else {
                    mCallback.fail(null);
                }
            }
        }
    }

}
