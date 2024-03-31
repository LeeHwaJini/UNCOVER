package com.shsoftnet.shcommon.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    public static String StackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
