/**
 * @Title: 	HttpUtil.java 
 * @Package com.cmcc.vrp.anhui.common.util 
 * @author:	sunyiwei
 * @date:	2015年6月3日 上午10:46:02 
 * @version	V1.0   
 */
package com.cmcc.seed.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName: HttpUtil
 * @Description: TODO
 * @author: sunyiwei
 * @date: 2015年6月3日 上午10:46:02
 * 
 */
public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doGet(String url, String queryString, String charset,
			boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);

		try {
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}

			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(
								System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				
				reader.close();
			}
		} catch (URIException e) {
			log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		
		return response.toString();
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param reqStr
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doPost(String url, String reqStr, String contentType, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		
		PostMethod method = new PostMethod(url);
		try {
			method.setRequestEntity(new StringRequestEntity(reqStr, contentType, "utf-8"));
			
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty){
						response.append(line).append(
								System.getProperty("line.separator"));
					}
					else{
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (UnsupportedEncodingException e1) {
			log.error(e1.getMessage());
			return null;
		}catch (IOException e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
			return null;
		} finally {
			method.releaseConnection();
		}
		
		return response.toString();
	}
	
	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param reqStr
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public static String doPost(String url, String reqStr, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		
		PostMethod method = new PostMethod(url);
		try {
			method.setRequestEntity(new StringRequestEntity(reqStr, "text/plain", "utf-8"));
			
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty){
						response.append(line).append(
								System.getProperty("line.separator"));
					}
					else{
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (UnsupportedEncodingException e1) {
			log.error(e1.getMessage());
			return null;
		}catch (IOException e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
			return null;
		} finally {
			method.releaseConnection();
		}
		
		return response.toString();
	}

	public static void main(String[] args) {
		String y = doGet("http://video.sina.com.cn/life/tips.html", null,
				"GBK", true);
		System.out.println(y);
	}
}
