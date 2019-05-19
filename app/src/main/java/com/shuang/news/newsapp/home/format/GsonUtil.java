package com.shuang.news.newsapp.home.format;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.shuang.news.newsapp.wrapper.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2019/4/9 下午2:12
 */
public class GsonUtil {

    @Nullable
    public static <T> List<T> format(String json, String key, Type type, int index) {
        try {
            JSONObject object = new JSONObject(json);
            String value = object.optString(key);
            JSONArray array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String url = jsonObject.optString("url");
                String url_3w = jsonObject.optString("url_3w");

                //移除第一个不规则项
                if (TextUtils.isEmpty(url) && TextUtils.isEmpty(url_3w))
                    array.remove(i);
                else {
                    break;
                }
            }

            return new Gson().fromJson(array.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("jsonFormat error : " + e.getMessage());
        }
        return null;
    }


}
