package com.cmcc.seed.codec;

/**
 * 验签接口：<br/>
 * 通过公钥，签名校验数据的有效性
 * 
 * @author qibaoguang at 2014年8月29日 上午10:30:08
 *
 */
public interface Verify {

    /**
     * 校验数字签名
     * 
     * @param data
     *            原始数据
     * @param sign
     *            数字签名
     * @return 验签成功:true，验签失败:false
     * @throws Exception
     */
    public boolean verify(byte[] data, String sign) throws Exception;

    /**
     * 验签
     * 
     * @param data
     *            原始数据
     * @param charset
     *            原始数据字符编码
     * @param sign
     *            数字签名
     * @return 验签结果：成功则true，失败则false
     * @throws Exception
     */
    public boolean verify(String data, String charset, String sign)
            throws Exception;

}
