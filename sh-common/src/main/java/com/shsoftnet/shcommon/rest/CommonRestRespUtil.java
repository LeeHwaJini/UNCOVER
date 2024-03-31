package com.shsoftnet.shcommon.rest;

import com.shsoftnet.shcommon.dto.AbsBaseException;
import com.shsoftnet.shcommon.dto.CommonRestResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class CommonRestRespUtil {

    public static <T> ResponseEntity<CommonRestResp<T>> OK(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.ok(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> OK(String msg) {
        return ResponseEntity.ok(CommonRestResp.OK(msg));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> OK(String msg, T body) {
        return ResponseEntity.ok(CommonRestResp.OK(msg, "", body));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> CREATED(String uri, CommonRestResp<T> commonRestResp) {
        return ResponseEntity.created(URI.create(uri)).body(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> CREATED(String uri, String msg, T body) {
        return ResponseEntity.created(URI.create(uri)).body(CommonRestResp.OK(msg, "", body));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> UPDATED() {
        return ResponseEntity.noContent().build();
    }

    public static <T> ResponseEntity<CommonRestResp<T>> UPDATED(String msg, T body) {
        return ResponseEntity.ok().body(CommonRestResp.OK(msg, "", body));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> DELETE() {
        return ResponseEntity.noContent().build();
    }
    public static <T> ResponseEntity<CommonRestResp<T>> DELETE(String msg, T body) {
        return ResponseEntity.ok().body(CommonRestResp.OK(msg, "", body));
    }


    public static <T> ResponseEntity<CommonRestResp<T>> UNAUTHORIZED(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> UNAUTHORIZED(AbsBaseException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonRestResp.ERROR(ex));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> FORBIDDEN(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> FORBIDDEN(AbsBaseException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonRestResp.ERROR(ex));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> NOT_FOUND(String msg) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonRestResp.ERROR(400, msg));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> NOT_FOUND(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonRestResp);
    }


    public static <T> ResponseEntity<CommonRestResp<T>> NOT_FOUND(AbsBaseException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonRestResp.ERROR(ex));
    }

    public static <T> ResponseEntity<CommonRestResp<T>> BAD_REQUEST(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.badRequest().body(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> BAD_REQUEST(AbsBaseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonRestResp.ERROR(ex));
    }

//    public static <T> ResponseEntity<CommonRestResp<T>> ACCEPTED(CommonRestResp<T> commonRestResp) {
//        return ResponseEntity.accepted().body(commonRestResp);
//    }

    public static <T> ResponseEntity<CommonRestResp<T>> INTERNAL_SERVER_ERROR(CommonRestResp<T> commonRestResp) {
        return ResponseEntity.internalServerError().body(commonRestResp);
    }

    public static <T> ResponseEntity<CommonRestResp<T>> INTERNAL_SERVER_ERROR(AbsBaseException ex) {
        return ResponseEntity.internalServerError().body(CommonRestResp.ERROR(ex));
    }




}
