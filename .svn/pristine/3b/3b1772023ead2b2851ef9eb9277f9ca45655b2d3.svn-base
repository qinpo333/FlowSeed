package com.cmcc.seed.codec;

/**
 * 签名接口：<br/>
 * 使用私钥加密数据，并对生成后的数据进行摘要操作
 * 
 * @author qibaoguang at 2014年8月29日 上午10:27:20
 *
 */
public interface Sign {

    /**
     * 对数据进行签名操作
     * 
     * @param data
     *            原始数据
     * @return 数字签名
     * @throws Exception
     */
    public String sign(byte[] data) throws Exception;

    /**
     * 数据签名
     * 
     * @param data
     *            原始数据
     * @param charset
     *            原始数据的字符编码
     * @return 数字签名
     * @throws Exception
     */
    public String sign(String data, String charset) throws Exception;

}
