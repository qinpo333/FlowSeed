/**
 * @Title: 	StringUtils.java 
 * @Package com.cmcc.vrp.util 
 * @author:	sunyiwei
 * @date:	2015年3月11日 下午4:25:25 
 * @version	V1.0   
 */
package com.cmcc.seed.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtils
 * @Description: 工具类
 * @author: sunyiwei
 * @date: 2015年3月11日 下午4:25:25
 * 
 */
public class StringUtils {
	public static String CODE_REGEX="^[1-9]\\d{5}$";
	public static String LOWER_UPPER_UNDERLINE_NUMBER_REGEX = "^[A-Za-z0-9_]+$";
	public static String MOBILE_REGEX = "^1[3-8][0-9]{9}$";
	public static String idCard_REGEX_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	public static String idCard_REGEX_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
	public static final int OPEN_ID_LENGTH = 28;
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT = "HH:mm:ss";	
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static boolean isValidCode(String code){
		if(org.apache.commons.lang.StringUtils.isBlank(code)){
			return false;
		}
//		return Pattern.compile(CODE_REGEX).matcher(code).matches();
		return true;
	}

	public static boolean isValidMd5Openid(String md5){
		if(md5.length()!=32){
			return false;
		}
		return true;
	}

	public static boolean isValidMobile(String mobile){
		if(org.apache.commons.lang.StringUtils.isBlank(mobile)){
			return false;
		}
		
		return Pattern.compile(MOBILE_REGEX).matcher(mobile).matches();
	}
	/**
		valid name,idcard and address
		@author lzz
	 	@time 2015-08-03
	 */
	public static boolean isValidName(String name){
		if(org.apache.commons.lang.StringUtils.isBlank(name)){
			return false;
		}

		return name.length()>1;
	}

	public static boolean isValidIdCard(String idCard){
		if(org.apache.commons.lang.StringUtils.isBlank(idCard)){
			return false;
		}
		if(idCard.length()==15){
			return Pattern.compile(idCard_REGEX_15).matcher(idCard).matches();
		}else if(idCard.length()==18){
			return Pattern.compile(idCard_REGEX_18).matcher(idCard).matches();
		}else{
			return false;
		}
	}

	public static  boolean isValidAddress(String address){
		if(org.apache.commons.lang.StringUtils.isBlank(address)){
			return false;
		}
		return address.length()>0;
	}
	
	/**
	 * 
	 * @Title:isValidPassword
	 * @Description: 验证一个字符串是否为有效的密码 有效的密码为： 6~16位的 字母、数字及下划线组成的字符
	 * @param szPassword 密码字符串
	 * @return
	 * @throws
	 * @author: sunyiwei
	 */
	public static boolean isValidPassword(String szPassword) {
		if (szPassword == null) {
			return false;
		}

		return szPassword.length() >= 6
				&& szPassword.length() <= 16
				&& Pattern.compile(LOWER_UPPER_UNDERLINE_NUMBER_REGEX)
						.matcher(szPassword).matches();
	}
	
	/**
	 * 
	 * @Title:isNumeric
	 * @Description: 判断String是否是数字，静态方法
	 * @param str 字符串
	 * @return
	 * @throws
	 * @author:
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}


	/**
	 * 
	 * @Title:isEmpty
	 * @Description: 判断String是否为空，静态方法
	 * @param str 字符串
	 * @return
	 * @throws
	 * @author:
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	* @Title:trimString 
	* @Description: 截取字符串前后的空格符，如果为null，直接返回
	* @param string
	* @return
	* @throws
	* @author:	sunyiwei
	 */
	public static String trimString(String string){
		if(string == null){
			return null;
		}
		
		return string.trim();
	}

	/*
	 * 判断是否为有效的微信openId, 判断规则为长度为28
	 */
	public static boolean isValidOpenId(String openId){
		if(org.apache.commons.lang.StringUtils.isBlank(openId) 
				|| openId.length() != OPEN_ID_LENGTH){
			return false;
		}
		
		return true;
	}
}
