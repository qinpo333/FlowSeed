package com.cmcc.seed.codec;

import org.apache.commons.codec.binary.Base64;

import java.security.Signature;
import java.security.SignatureException;

/**
 * 验签接口抽象实现类（非线程安全）
 * 
 * @author qibaoguang at 2014年9月1日 上午11:21:46
 * @see com.iapppay.security.api.Verify
 */
public abstract class BaseVerify implements Verify {

    protected Signature signature;

    @Override
    public boolean verify(byte[] data, String sign) throws Exception {
        try {
            signature.update(data);
            return signature.verify(Base64.decodeBase64(sign));
        } catch (SignatureException e) {
            this.signature = createSignature();
            throw e;
        }
    }

    @Override
    public boolean verify(String data, String charset, String sign)
            throws Exception {
        return verify(data.getBytes(charset), sign);
    }

    /**
     * 创建签名验签使用的Signature
     */
    protected abstract Signature createSignature() throws Exception;
}
