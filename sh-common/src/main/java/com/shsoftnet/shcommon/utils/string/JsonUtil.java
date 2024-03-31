package com.shsoftnet.shcommon.utils.string;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.time.LocalDateTime;

public class JsonUtil {

    public static String ToJsonStrFromObj(Object object) {
        return new GsonBuilder().setLenient().create().toJson(object);
    }

    public static <T> T ToObjFromJsonStr(String jsonStr, Class<T> clazz) {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).setLenient().create().fromJson(jsonStr, clazz);
    }


}
