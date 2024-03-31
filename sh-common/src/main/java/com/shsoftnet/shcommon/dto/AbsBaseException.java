package com.shsoftnet.shcommon.dto;

public abstract class AbsBaseException extends Exception{

    public AbsBaseException() {}

    public AbsBaseException(Exception e) {
        super(e);
    }

    public abstract long getErrorCode();
    public abstract String getErrorMsg();
}
