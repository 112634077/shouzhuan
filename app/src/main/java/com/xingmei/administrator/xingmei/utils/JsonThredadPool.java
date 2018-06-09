package com.xingmei.administrator.xingmei.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonThredadPool {
    /**
     * 解析数组
     * @param jsonObject 需要解析的json对象
     * @param result 需要解析的json字段
     * @return 返回jsonarray对象
     * @throws Exception 抛出异常
     */
    //解析数组
    public static JSONArray analysisJsonArray(JSONObject jsonObject,String result) throws Exception{
        return jsonObject.getJSONArray(result);

    }

    /**
     * 解析json并返回json对象
     * @param result 需要解析的json数据
     * @return 返回json对象
     * @throws Exception 抛出异常
     */
    //解析json并返回json对象
    public static JSONObject analysisJsonObject(String result) throws Exception{
        return new JSONObject(result);
    }
}
