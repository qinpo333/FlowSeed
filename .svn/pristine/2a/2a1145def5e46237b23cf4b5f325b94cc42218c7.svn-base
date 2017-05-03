package com.cmcc.seed.codec;

import org.apache.commons.codec.binary.Base64;

import java.security.Signature;

/**
 * 签名接口抽象实现类（非线程安全）
 * 
 * @author qibaoguang at 2014年9月1日 上午11:11:35
 * @see com.iapppay.security.api.Sign
 */
public abstract class BaseSign implements Sign {

    protected Signature signature;

    @Override
    public String sign(byte[] data) throws Exception {
        try {
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            this.signature = createSignature();
            throw e;
        }
    }

    @Override
    public String sign(String data, String charset) throws Exception {
        return sign(data.getBytes(charset));
    }

    /**
     * 创建签名验签使用的Signature
     */
    protected abstract Signature createSignature() throws Exception;
}
