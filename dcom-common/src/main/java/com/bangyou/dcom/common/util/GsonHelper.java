package com.bangyou.dcom.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json转换
 * @author radiance
 */
public class GsonHelper {


    public static <T> String toJson(T oValue) {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(oValue);
    }


    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String sJson, Class<T> tClass) {
        return (T) new Gson().fromJson(sJson, tClass);
    }

}
