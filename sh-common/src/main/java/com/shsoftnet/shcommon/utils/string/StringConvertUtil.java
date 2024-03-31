package com.shsoftnet.shcommon.utils.string;

import com.google.common.primitives.Bytes;
import com.shsoftnet.shcommon.utils.numbers.UnsignedByte;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class StringConvertUtil {

    public static byte[] StringNumbersToByteArray(String str, String spl) {
        String[] splTmp = str.split(spl);

        byte[] tmpBytes = Arrays.stream(splTmp)
                .map(Short::parseShort)
                .map(UnsignedByte::parse)
                .reduce(new byte[0], (BinaryOperator<byte[]>) Bytes::concat);

        return tmpBytes;
    }

    public static byte[] IpAddressStrToByteArray(String ipAddress) {
        return StringConvertUtil.StringNumbersToByteArray(ipAddress, "\\.");
    }


}
