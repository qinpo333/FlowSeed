package com.cmcc.seed.codec;

import net.oauth.signature.pem.PKCS1EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * 基于PKCS1规范的RSA私钥签名实现
 * 
 * @author qibaoguang at 2014年9月1日 上午11:10:53
 * @see com.iapppay.security.api.impl.BaseSign
 */
public class RSAPKCS1Sign extends BaseSign {

    private PrivateKey privateKey;

    /**
     * 构造基于PKCS1+RSA的私钥签名对象
     * 
     * @param rsaPrivateKey
     *            base64编码的rsa私钥
     * @throws Exception
     */
    public RSAPKCS1Sign(String rsaPrivateKey) throws Exception {
        this(Base64.decodeBase64(rsaPrivateKey));
    }

    public RSAPKCS1Sign(byte[] bytes) throws Exception {
        privateKey = getKey(bytes);
        signature = createSignature();
    }

    private PrivateKey getKey(byte[] bytes) throws Exception {
        // 解密由base64编码的私钥
        PKCS1EncodedKeySpec privateKeyPKCS1 = new PKCS1EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory
                .getInstance(Constants.RSA_KEY_ALGORITHM);
        return keyFactory.generatePrivate(privateKeyPKCS1.getKeySpec());
    }

    @Override
    protected Signature createSignature() throws Exception {
        // 用私钥对信息生成数字签名
        Signature signature = Signature
                .getInstance(Constants.RSA_SIGNATURE_ALGORITHM);
        // 初始化signature
        signature.initSign(privateKey);
        return signature;
    }

}
