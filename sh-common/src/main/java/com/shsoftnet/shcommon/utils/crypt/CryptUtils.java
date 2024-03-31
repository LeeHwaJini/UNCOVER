package com.shsoftnet.shcommon.utils.crypt;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class CryptUtils {

    /**
     * Aes 인스턴스
     */
    public static Aes aes = new Aes();
    /**
     * Digest 인스턴스
     */
    public static Digest digest = new Digest();

    /**
     * Aes 인스턴스를 반환한다.
     */
    public static Aes getAES() {
        return aes;
    }//:

    /**
     * Digest 인스턴스를 반환한다.
     */
    public static Digest getDigest() {
        return digest;
    }

    /**
     * MessageDigest를 이용한 HASH 값을 생성한다.
     *
     * @author behap
     */
    public static class Digest {

        /**
         * 문자열을 HASH 값으로 변환한다.
         *
         * @param strToDigest 변환할 문자열
         * @param algorithm   알고리즘.
         * @return 변환된 바이트 배열
         * @throws Exception
         */
        public byte[] digest(String strToDigest, AlgorithmEnum algorithm) {
            try {
                //final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                final MessageDigest digest = MessageDigest.getInstance(algorithm.getAlgorithm());
                final byte[] hashbytes = digest.digest(strToDigest.getBytes(StandardCharsets.UTF_8));
                return hashbytes;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public byte[] digest(byte[] bytesToDigest, AlgorithmEnum algorithm) {
            try {
                //final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                final MessageDigest digest = MessageDigest.getInstance(algorithm.getAlgorithm());
                final byte[] hashbytes = digest.digest(bytesToDigest);
                return hashbytes;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 문자열을 해쉬값으로 변환하고 Hex 문자열로 반환한다.
         *
         * @param strToDigest 변환할 문자열
         * @param algorithm   알고리즘
         * @return 변환된 문자열
         * @throws Exception
         */
        public String digestToHex(String strToDigest, AlgorithmEnum algorithm) {
            try {
                byte[] bytesDigested = digest(strToDigest, algorithm);
                return HexConverter.bytesToHex(bytesDigested);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }//:

    }//:

    /**
     * AES 암호화를 지원한다.  암호화를 위한 키는 16바이트여야 한다.
     */
    public static class Aes {
        static SecureRandom rnd = new SecureRandom();
        static IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));


        /**
         * 문자열을 암호화 한다.
         *
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return 암호화된 문자열을 바이트 배열로 반환한다.
         * @throws Exception
         */

        public byte[] encryptToBytesModeECB(String key, String strToEncrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

        }//:

        public byte[] encryptToBytesModeECB(String key, byte[] bytesToEncrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(bytesToEncrypt);

        }//:

        /**
         * 문자열을 암호화 한다.
         *
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return 암호화된 문자열을 base64로 encode해서  반환한다
         * @throws Exception
         */
        public String encryptModeECB(String key, String strToEncrypt) throws Exception {
            String encryptedStr = Base64.getEncoder().encodeToString(encryptToBytesModeECB(key, strToEncrypt));
            return encryptedStr;
        }//:

        /**
         * 암호화된 바이트 배열을 복호화 한다.
         *
         * @param key            암호화 키
         * @param bytesToDecrypt 복호화할 바이트 배열
         * @return 복호화된 바이트 배열
         * @throws Exception
         */
        public byte[] decryptToBytesModeECB(String key, byte[] bytesToDecrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(bytesToDecrypt);
        }//:


        /**
         * Base 64로 감싸진 문자열을 복호화 한다.
         *
         * @param key          암호화 키
         * @param strToDecrypt 복호화할 문자열
         * @return 복호화된 문자열
         * @throws Exception
         */
        public String decryptModeECB(String key, String strToDecrypt) throws Exception {
            byte[] bytesToDecrypt = Base64.getDecoder().decode(strToDecrypt);
            String decreptedStr = new String(decryptToBytesModeECB(key, bytesToDecrypt));
            return decreptedStr;
        }//:














        /**
         * 문자열을 암호화 한다.
         *
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return 암호화된 문자열을 바이트 배열로 반환한다.
         * @throws Exception
         */

        public byte[] encryptToBytesModeCBC(String iv, String key, String strToEncrypt) throws Exception {
            return encryptToBytesModeCBC(iv.getBytes(StandardCharsets.UTF_8), key, strToEncrypt.getBytes(StandardCharsets.UTF_8));
        }//:

        public byte[] encryptToBytesModeCBC(String iv, String key, byte[] bytesToEncrypt) throws Exception {
            return encryptToBytesModeCBC(iv.getBytes(StandardCharsets.UTF_8), key, bytesToEncrypt);
        }//:

        public byte[] encryptToBytesModeCBC(byte[] iv, String key, byte[] bytesToEncrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(bytesToEncrypt);

        }//:

        /**
         * 문자열을 암호화 한다.
         *
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return 암호화된 문자열을 base64로 encode해서  반환한다
         * @throws Exception
         */
        public String encryptModeCBC(String iv, String key, String strToEncrypt) throws Exception {
            String encryptedStr = Base64.getEncoder().encodeToString(encryptToBytesModeCBC(iv, key, strToEncrypt));
            return encryptedStr;
        }//:

        /**
         * 암호화된 바이트 배열을 복호화 한다.
         *
         * @param key            암호화 키
         * @param bytesToDecrypt 복호화할 바이트 배열
         * @return 복호화된 바이트 배열
         * @throws Exception
         */
        public byte[] decryptToBytesModeCBC(String iv, String key, byte[] bytesToDecrypt) throws Exception {
            return decryptToBytesModeCBC(iv.getBytes(StandardCharsets.UTF_8), key, bytesToDecrypt);
        }//:

        public byte[] decryptToBytesModeCBC(byte[] iv, String key, byte[] bytesToDecrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(bytesToDecrypt);
        }//:


        /**
         * Base 64로 감싸진 문자열을 복호화 한다.
         *
         * @param key          암호화 키
         * @param strToDecrypt 복호화할 문자열
         * @return 복호화된 문자열
         * @throws Exception
         */
        public String decryptModeCBC(String iv, String key, String strToDecrypt) throws Exception {
            byte[] bytesToDecrypt = Base64.getDecoder().decode(strToDecrypt);
            String decreptedStr = new String(decryptToBytesModeCBC(iv, key, bytesToDecrypt));
            return decreptedStr;
        }//:




    }
}
