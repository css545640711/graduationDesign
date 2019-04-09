package com.shuang.news.newsapp.home.format;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shuang.news.newsapp.wrapper.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:12
 */
public class GsonUtil {

    public static <T> List<T> format(String json, Type type, int index) {
        try {
            JSONObject object = new JSONObject(json);
            Iterator<String> keys = object.keys();
            String value = "";
            while (keys.hasNext()) {
                value = object.getString(keys.next());
            }
            JSONArray array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String url = jsonObject.optString("url");
                if (TextUtils.isEmpty(url))
                    array.remove(i);
                else {
                    break;
                }
            }

            return new Gson().fromJson(value, type);
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("jsonFormat error : " + e.getMessage());
        }
        return new ArrayList<>();
    }


}
