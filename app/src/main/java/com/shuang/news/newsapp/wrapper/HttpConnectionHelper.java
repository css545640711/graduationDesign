package com.shuang.news.newsapp.wrapper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpConnectionHelper {

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
            while (inputStream.read(buffer) != -1) {
                String s = new String(buffer, Charset.forName("utf-8"));
                builder.append(s);
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
