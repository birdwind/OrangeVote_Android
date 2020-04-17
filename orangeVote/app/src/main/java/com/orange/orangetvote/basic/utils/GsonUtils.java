package com.orange.orangetvote.basic.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装的是使用Gson解析json的方法
 *
 */
public class GsonUtils {

    /**
     * 把一個json變成對象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    /**
     * 把jsonArray變成對象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseJsonFromJsonArray(String json, Class<T> clazz) {
        List<T> lst = null;
        try {
            lst = new ArrayList<T>();

            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                if(elem.isJsonObject()){
                    LogUtils.d("這是Object拉");
                }
                if( elem instanceof JsonObject){
                    lst.add(parseJsonToBean(elem.getAsJsonObject().getAsString(), clazz));
                }else {
                    lst.add(parseJsonToBean(elem.getAsString(), clazz));
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return lst;
    }


    /**
     * 對象轉為json字串
     *
     * @param target
     * @return
     */
    public static String toJson(Object target) {
        Gson gson = new Gson();
        return gson.toJson(target);
    }


    /**
     * 獲取json串中某個字段的值，注意，只能獲取同一層级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

        return value;
    }
}
