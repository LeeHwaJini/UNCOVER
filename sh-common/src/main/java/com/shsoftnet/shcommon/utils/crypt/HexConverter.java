package com.shsoftnet.shcommon.utils.crypt;

public class HexConverter {

    public static String bytesToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }

}
