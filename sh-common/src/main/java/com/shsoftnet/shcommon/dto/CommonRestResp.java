package com.shsoftnet.shcommon.dto;


import com.google.gson.*;
import com.shsoftnet.shcommon.utils.exception.ExceptionUtils;
import com.shsoftnet.shcommon.utils.string.GsonLocalDateTimeAdapter;
import com.shsoftnet.shcommon.utils.string.JsonUtil;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class CommonRestResp<T> {
    public static final int RESULT_CODE_OK = 200;
    public static final int RESULT_CODE_ERROR = 500;

    long resCd; // 응답 코드
    String msg; // 사용자 전달 메시지
    String desc; // 부가 정보,에러 관련?
    LocalDateTime tracTime;

    T data; // 주고 받는 데이터

    public long getResCd() {
        return resCd;
    }

    public CommonRestResp<T> setResCd(long resCd) {
        this.resCd = resCd;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public CommonRestResp<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public CommonRestResp<T> setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public LocalDateTime getTracTime() {
        return tracTime;
    }

    public CommonRestResp<T> setTracTime(LocalDateTime tracTime) {
        this.tracTime = tracTime;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonRestResp<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> CommonRestResp<T> OK() {
        return CommonRestResp.OK("OK", "", null);
    }

    public static <T> CommonRestResp<T> OK(String msg) {
        return CommonRestResp.OK(msg, "", null);
    }

    public static <T> CommonRestResp<T> OK(T data) {
        return CommonRestResp.OK("OK", "", data);
    }


    public static <T> CommonRestResp<T> OK(String msg, String desc) {
        return CommonRestResp.OK(msg, desc, null);
    }

    public static <T> CommonRestResp<T> OK(String msg, String desc, T data) {
        return new CommonRestResp<T>()
                .setTracTime(LocalDateTime.now())
                .setResCd(RESULT_CODE_OK)
                .setMsg(msg)
                .setDesc(desc)
                .setData(data)
                ;
    }

    public static <T> CommonRestResp<T> ERROR(long errorCode, String msg) {
        return new CommonRestResp<T>()
                .setTracTime(LocalDateTime.now())
                .setResCd(errorCode)
                .setMsg(msg)
                ;
    }

    public static <T> CommonRestResp<T> ERROR(AbsBaseException e) {
        return ERROR(e.getErrorCode(), e.getErrorMsg(), ExceptionUtils.StackTraceToString(e));
    }

    public static <T> CommonRestResp<T> ERROR(AbsBaseException e, T data) {
        return ERROR(e.getErrorCode(), e.getErrorMsg(), ExceptionUtils.StackTraceToString(e), data);
    }


    public static <T> CommonRestResp<T> ERROR(long errorCode, String msg, String desc) {
        return new CommonRestResp<T>()
                .setTracTime(LocalDateTime.now())
                .setResCd(errorCode)
                .setMsg(msg)
                .setDesc(desc)
                ;
    }

    public static <T> CommonRestResp<T> ERROR(long errorCode, String msg, String desc, T data) {
        return new CommonRestResp<T>()
                .setTracTime(LocalDateTime.now())
                .setResCd(errorCode)
                .setMsg(msg)
                .setDesc(desc)
                .setData(data)
                ;
    }


    public static CommonRestResp FromJsonStr(String jsonStr) {
        return new GsonBuilder().setLenient().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create().fromJson(jsonStr, CommonRestResp.class);
    }

    public static <T> T FromJsonStrToDataObj(String jsonStr, Class<T> clazz) {
        return JsonUtil.ToObjFromJsonStr(CommonRestResp.FromJsonStr(jsonStr).getData().toString(), clazz);
    }

    public static Object FromJsonStrToDataObj(String jsonStr, Type clazz) {
        return new GsonBuilder().setLenient().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create().fromJson(jsonStr, clazz);
    }

}
