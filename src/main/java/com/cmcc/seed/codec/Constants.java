package com.cmcc.seed.codec;

public interface Constants {

    /**
     * RAS算法key
     */
    String RSA_KEY_ALGORITHM = "RSA";

    /**
     * RSA PKCS1加密解密算法key
     */
    String RSA_PKCS1_KEY = "RSA/ECB/PKCS1Padding";

    /**
     * 默认编码
     */
    String DEFAULT_CHARSET = "UTF-8";

    /**
     * RSA签名算法:MD5+RSA
     */
    String RSA_SIGNATURE_ALGORITHM = "MD5withRSA";
}
