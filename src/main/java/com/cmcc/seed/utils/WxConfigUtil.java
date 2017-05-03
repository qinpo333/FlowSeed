package com.cmcc.seed.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public class WxConfigUtil {
	public static void main(String[] args) {
		String jsapi_ticket = "jsapi_ticket";

//		// 注意 URL 一定要动态获取，不能 hardcode
//		String url = "http://example.com";
//		Map<String, String> ret = sign(jsapi_ticket, url);
//		for (Map.Entry entry : ret.entrySet()) {
//			System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
	};

	public static WxConfig getWxconfig(String jsapi_ticket, String url, String appid) {
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;
		System.out.println(string1);

		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		WxConfig wxConfig = new WxConfig();
		wxConfig.setUrl(url);
		wxConfig.setJsapi_ticket(jsapi_ticket);
		wxConfig.setTimestamp(timestamp);
		wxConfig.setAppid(appid);
		wxConfig.setSignature(signature);
		wxConfig.setNonceStr(nonce_str);
		wxConfig.setErrcode("0");

		return wxConfig;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
