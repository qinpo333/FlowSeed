package com.cmcc.seed.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密算法
 * @author  liuzengzeng
 * @time 2015年11月15日18:53:33
 */
public class AESUtil
{

    public static String encrypt(String encryptionKey,String plainText) throws Exception
    {
        Cipher cipher = getCipher(encryptionKey,Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }

    public static String decrypt(String encryptionKey,String encrypted) throws Exception
    {
        Cipher cipher = getCipher(encryptionKey,Cipher.DECRYPT_MODE);
        byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(plainBytes);
    }

    private static Cipher getCipher(String encryptionKey,int cipherMode)
            throws Exception
    {
        String encryptionAlgorithm = "AES";
        SecretKeySpec keySpecification = new SecretKeySpec(
                encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);

        return cipher;
    }

    public static String encryptWithString(String plainText){
        try{
            return encrypt("MZygGHjJsCpRrfOr",plainText);
        }catch (Exception e){
            return "";
        }
    }

    public static String decryptWithString(String plainText){
        try{
            return decrypt("MZygGHjJsCpRrfOr",plainText);
        }catch (Exception e){
            return "";
        }
    }
}
