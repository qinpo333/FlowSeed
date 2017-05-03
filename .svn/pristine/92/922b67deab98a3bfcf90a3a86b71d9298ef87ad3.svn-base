package com.cmcc.seed.controller;

import com.alibaba.fastjson.JSON;
import com.cmcc.seed.model.User;
import com.cmcc.seed.pojo.WebMobilePojo;
import com.cmcc.seed.utils.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public abstract class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	protected final String AUTH_ERROR_PAGE = "redirect:http://wx.4ggogo.com/lottery/game/index.html"; //redirect:/game/index.html

	protected final  String TEST_AUTH_ERROR_PAGE = "redirect:http://wx.4ggogo.com/lottery/gameTest/index.html"; //redirect:gameTest/index.html

	private final String ACTIVE_ID = "hebeisecond_";

	public final String SESSION_KEY = ACTIVE_ID+"wxOpenId";

	public final String SESSION_MD5_KEY = ACTIVE_ID+"wxOpenId_MD5";

	public final String USER_INFO_KEY = ACTIVE_ID+"user_info_";

	public final String SHARE_KEY = ACTIVE_ID+"share_";

	public final String COUNT_KEY = ACTIVE_ID+"count_";

	public final String SESSION_MOBILE_KEY = ACTIVE_ID+"mobile";

	public final String REDIS_MOBILE_KEY = ACTIVE_ID+"mobile_";

	public final String BLACK_KEY = ACTIVE_ID+"black_";

	public final String FLAG_KEY = ACTIVE_ID+"flag_";

	public final String LOCK_KEY = ACTIVE_ID+"lock_";

	public final String CHARGE_KEY = ACTIVE_ID+"charge_";

	public final String INSERT_KEY = ACTIVE_ID+"insert_";

	public final String ENCRY_KEY = "MZygGHjJsCpRrfOr";

	public final String JSSDK_KEY = ACTIVE_ID+"jssdk";

	public final String BLANK_NOTE = ACTIVE_ID+"whitenote_";

	@Value("#{urlAPIsettings['configAddr']}")
	protected String configAddr;
	@Value("#{urlAPIsettings['getMobileUrl']}")
	protected String getMobileUrl;
	@Value("#{urlAPIsettings['cmccTokenUrl']}")
	protected String cmccTokenUrl;
	@Value("#{urlAPIsettings['accountId']}")
	protected String accountId;
	@Value("#{urlAPIsettings['appid10086']}")
	protected String appid10086;
	@Value("#{urlAPIsettings['webTokenGetUserInfoUrl']}")
	protected String webTokenGetUserInfoUrl;
	@Value("#{urlAPIsettings['REDIRECT_GAME_PAGE']}")
	protected String REDIRECT_GAME_PAGE;


	@Value("#{urlAPIsettings['ShowListstartTime']}")
	protected String startTime;
	@Value("#{urlAPIsettings['ShowListendTime']}")
	protected String endTime;
	@Value("#{urlAPIsettings['ChargeDeadLine']}")
	protected String chargeTime;
	@Value("#{urlAPIsettings['debugState']}")
	protected String debugState;
	@Value("#{urlAPIsettings['ERROR_PAGE']}")
	protected String ERROR_PAGE;

	@Value("#{urlAPIsettings['NOT_START_PAGE']}")
	protected String NOT_START_PAGE;
	@Value("#{urlAPIsettings['END_PAGE']}")
	protected String END_PAGE;
	@Value("#{urlAPIsettings['NEED_FOCUS_PAGE']}")
	protected String NEED_FOCUS_PAGE;
	@Value("#{urlAPIsettings['rollAddr']}")
	protected String rollAddr; //抽奖服务的地址
	@Value("#{urlAPIsettings['CHARGE_PAGE']}")
	protected String CHARGE_PAGE;
	@Value("#{urlAPIsettings['DIDI_PAGE']}")
	protected String DIDI_PAGE;



	@Value("#{urlAPIsettings['getMessageurl']}")
	private String getMessageurl;
	@Value("#{urlAPIsettings['getBindUrl']}")
	private String getBindUrl;
	@Value("#{urlAPIsettings['getFansInfoUrl']}")
	private String getFansInfoUrl;

	//公众号jssdkAPI
	@Value("#{urlAPIsettings['jssdkAPI']}")
	protected String jssdkAPI;

	//pages
	@Value("#{urlAPIsettings['INDEX_PAGE']}")
	protected String indexPage;
	@Value("#{urlAPIsettings['RULE_PAGE']}")
	protected String rulePage;
	@Value("#{urlAPIsettings['RECOREDS_PAGE']}")
	protected String recordsPage;
	@Value("#{urlAPIsettings['BIND_PAGE']}")
	protected String bindPage;
	@Value("#{urlAPIsettings['WRITE_PAGE']}")
	protected String writePage;
	@Value("#{urlAPIsettings['HELP_PAGE']}")
	protected String helpPage;
	@Value("#{urlAPIsettings['WAREHOUSE_PAGE']}")
	protected String warehousePage;
	@Value("#{urlAPIsettings['EXCHANGE_PAGE']}")
	protected String exchangePage;
	@Value("#{urlAPIsettings['FLOWDETAIL_PAGE']}")
	protected String flowDetailPage;
	@Value("#{urlAPIsettings['FRIENDS_PAGE']}")
	protected String friendsPage;
	@Value("#{urlAPIsettings['FRIEND_PAGE']}")
	protected String friendPage;
	@Value("#{urlAPIsettings['404_PAGE']}")
	protected String errorPage;
	@Value("#{urlAPIsettings['GIVE_PAGE']}")
	protected String givePage;
	@Value("#{urlAPIsettings['GIVERESULT_PAGE']}")
	protected String giveResultPage;



	/**
	 * @return
	 * @throws
	 * @Title:getSession
	 * @Description: 返回当前会话
	 * @author: sunyiwei
	 */
	public HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
			logger.error("获取session异常", e);
		}
		return session;
	}

	/**
	 * @return
	 * @throws
	 * @Title:getRequest
	 * @Description: 返回请求
	 * @author: sunyiwei
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (attrs == null) {
			return null;
		}
		return attrs.getRequest();
	}



	private User getUserInfo(String wxOpenid) {
		if (!StringUtils.isValidOpenId(wxOpenid)) {
			return null;
		}

		User userInfo = new User();
		userInfo.setCreateTime(new Date());
		userInfo.setUpdateTime(new Date());
		userInfo.setOpenId(wxOpenid);
		userInfo.setMobile(getMobile());
		userInfo.setNickname("");
		userInfo.setImgUrl("");
		return userInfo;
	}

	private User getUserInfo(String wxOpenid,String mobile) {
		if (!StringUtils.isValidOpenId(wxOpenid)) {
			return null;
		}

		User userInfo = new User();
		userInfo.setCreateTime(new Date());
		userInfo.setUpdateTime(new Date());
		userInfo.setOpenId(wxOpenid);
		userInfo.setMobile(mobile);
		userInfo.setNickname("");
		userInfo.setImgUrl("");
		return userInfo;
	}


	/**
	 * @Description: 得到当前登录的openid
	 * @author: liuzengzeng
	 * @time 2015年10月12日20:10:55
	 */
	public String getWxOpenid() {

		String wxOpenid = (String) getSession().getAttribute(SESSION_KEY);
		return wxOpenid;
	}

	/**
	 * 将当前登录用户的openid放到session中
	 * @time 2015年10月12日20:11:12
	 */
	public boolean setWxOpenid(String wxOpenid) {
		//TODO:测试阶段先不校验
		/*if (!StringUtils.isValidOpenId(wxOpenid)) {
			return false;
		}*/
		getSession().setAttribute(SESSION_KEY, wxOpenid);
		logger.info("sessionId = " + getSession().getId());
		return true;
	}

	/**
	 * @Description: 得到当前登录的openid的MD5
	 * @author: liuzengzeng
	 * @time 2015年10月12日20:10:55
	 */
	public String getMD5WxOpenId() {

		String MD5WxOpenid = (String) getSession().getAttribute(SESSION_MD5_KEY);
		return MD5WxOpenid;
	}


	/**
	 * 得到当前用户的mobile
	 *
	 * @return
	 */
	public String getMobile() {

		String mobile = (String) getSession().getAttribute(SESSION_MOBILE_KEY);
		return mobile;
	}

	/**
	 * 将当前登录用户的mobile放到session中
	 * @time 2015年10月12日20:18:43
	 */
	public boolean setMobile(String mobile) {
		if (!StringUtils.isValidMobile(mobile)) {
			return false;
		}
		getSession().setAttribute(SESSION_MOBILE_KEY, mobile);
		return true;
	}

	public void setTmpWxOpenId() {
//		String randomOpenId = RandomStringUtils.randomAlphabetic(28);
//		getSession().setAttribute(SESSION_KEY,randomOpenId);
		getSession().setAttribute(SESSION_KEY,"oJAS9uOUHqa4OrIfkQC9p3WZY8vk");
	}

	//距离今天结束的秒数
	public int getExpire() {
		DateTime beginDt = new DateTime();  //当前时间
		DateTime endDt = new DateTime(beginDt.getYear(), beginDt.getMonthOfYear(), beginDt.getDayOfMonth(), 23, 59, 59);

		return Math.abs(Seconds.secondsBetween(beginDt, endDt).getSeconds());
	}

	//10086第一个接口，利用code换取openid
	public String getOpenIdWithCode(String accountId,String code){
		String resBody =  HttpUtil.doGet(cmccTokenUrl, "accountId=" + accountId + "&code=" + code, "utf-8", true);
		logger.info("Call 10086 NO.1 API response:"+resBody);
		return resBody;
	}

	//10086第二个接口，验证是否绑定手机
	public String mobibleBody() {
		logger.info("openid:"+getWxOpenid());
		long startTime = System.currentTimeMillis();
		String resBody = HttpUtil.doGet(getMobileUrl, "openId=" + getWxOpenid(), "utf-8", true);
		logger.info("Call 10086 NO.2 API response:" + resBody + ";openid:" + getWxOpenid() + ",耗时：" + (System.currentTimeMillis() - startTime));
		return resBody;
	}

	//10086发送验证码
	public String getMessgeBody(String telNum) {
		String resBody = HttpUtil.doGet(getMessageurl, "openId=" + getWxOpenid() + "&telNum=" + telNum, "utf-8", true);
		logger.info("Call 10086 NO.3 API response:" + resBody+";openId:"+getWxOpenid()+";mobile:"+telNum);
		return resBody;
	}

	//10086绑定接口
	public String toBindBody(String telNum,String captcha) {
		String resBody = HttpUtil.doGet(getBindUrl, "openId=" + getWxOpenid() + "&telNum=" + telNum + "&captcha=" + captcha, "utf-8", true);
		logger.info("Call 10086 NO.4 API response:" + resBody + ";openId" + getWxOpenid() + ";mobile:" + telNum + ";captcha:" + captcha);
		return resBody;
	}

	//jssdk接口，获取tickets
	 public String getTokenTicket() {
		String resBody =  HttpUtil.doGet(jssdkAPI, "accountId=" + accountId, "UTF-8", true);
     	logger.info("Call 10086 NO.5 API response:" + resBody);
		return resBody;
	}

	public String getFansInfo(String accountId, String code) {
		long startTime = System.currentTimeMillis();
		String resBody =  HttpUtil.doGet(getFansInfoUrl, "accountId=" + accountId + "&code=" + code, "UTF-8", true);
		logger.info("调用10086接口获取用户信息，返回结果为:" + resBody + ",耗时：" + (System.currentTimeMillis() - startTime));
		return resBody;
	}


	/**
	 * 对必要信息做存储操作
	 *
	 * redis：
	 * openid：微信用户认证id
	 * userinfo_:用户信息
	 * share_:是否能够分享标志 true or false
	 * phone_:手机号 用于存储openid的md5字符串
	 *
	 * session
	 * mobile:手机号
	 * openId:
	 * openIdMD5:openid的md5字符串
	 * @param wxOpenid
	 * @time 2015年10月12日17:22:55
	 */
	public boolean storeSessionAndRedis(String wxOpenid){
		//TODO:测试阶段不校验
		/*if (!StringUtils.isValidOpenId(wxOpenid)) {
			return false;
		}*/
		/*ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			if (org.apache.commons.lang.StringUtils.isBlank(jedis.get(USER_INFO_KEY + wxOpenid))) {
				jedis.set(USER_INFO_KEY + wxOpenid, JSON.toJSONString(getUserInfo(wxOpenid)));//新用户进来
				logger.info("新用户："+wxOpenid);
			}
			if (org.apache.commons.lang.StringUtils.isBlank(jedis.get(SHARE_KEY + wxOpenid))) {
				jedis.set(SHARE_KEY+wxOpenid,"true");//未分享状态，能够进行分享操作
			}
			if (org.apache.commons.lang.StringUtils.isBlank(jedis.get(COUNT_KEY + wxOpenid))) {
				jedis.set(COUNT_KEY+wxOpenid,"0");//玩的次数设置为0
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}*/
		setWxOpenid(wxOpenid);
		return true;
	}


	/**
	 * 对redis中的用户信息做更新，添加手机号mobile,并添加手机号对应的openid的md5信息
	 * @param wxOpenid
	 * @param mobile
	 * @time 2015年10月12日20:00:22
	 */
	public boolean updateRedisMobile(Jedis jedis,String wxOpenid,String mobile) throws Exception{
		if (!StringUtils.isValidMobile(mobile)||!StringUtils.isValidOpenId(wxOpenid)) {
			return false;
		}
        if (!org.apache.commons.lang.StringUtils.isBlank(jedis.get(USER_INFO_KEY + wxOpenid))) {
            jedis.set(USER_INFO_KEY + wxOpenid, JSON.toJSONString(getUserInfo(wxOpenid,mobile)));
        }
        if(org.apache.commons.lang.StringUtils.isBlank(jedis.get(REDIS_MOBILE_KEY+mobile))){
            jedis.set(REDIS_MOBILE_KEY+mobile,wxOpenid);
        }
		return true;
	}

	//将手机号码存入redis和session中
	public boolean setMobileRedisAndSession(Jedis jedis) throws Exception
	{
		WebMobilePojo webMobilePojo = new WebMobilePojo();
		String responseMobileBody = mobibleBody();//第二个接口
		logger.info("api 2"+responseMobileBody);
		if (responseMobileBody == null || responseMobileBody.equals("")) {
			return false;
		} else {
			webMobilePojo = JSON.parseObject(responseMobileBody, WebMobilePojo.class);
			if (webMobilePojo.getStatus().equals("0")) {
				setMobile(webMobilePojo.getMobile());
				updateRedisMobile(jedis,getWxOpenid(),webMobilePojo.getMobile());
				return true;
			}
		}
		return false;
	}





	public WxConfig getwxConfig (String path){
//		Jedis jedis = null;
//		try{
//			jedis = pool.getResource();
        String wxconfig = (String)getSession().getAttribute(path);
		logger.info("wxconfig0===0======"+wxconfig);
        if(null==wxconfig||wxconfig.equals("")){
            return getHttpWxConfig(path);
        }
        else{
            return JSON.parseObject(wxconfig,WxConfig.class);
        }
//		}catch (Exception e){
//			logger.info(e.getMessage());
//			return null;
//		}finally {
//			jedis.close();
//		}
	}

	private WxConfig getHttpWxConfig(String path){
//		if(RedisLockUtil.tryLock(jedis,JSSDK_KEY,"true")) {
        String respStr = getTokenTicket();
        TicketToken ticketToken = JSON.parseObject(respStr, TicketToken.class);
        if (ticketToken.getStatus().equals("0")) {
            //获取签名
            WxConfig wxConfig = WxConfigUtil.getWxconfig(ticketToken.getTicket(), path, appid10086);
            getSession().setAttribute(path,JSON.toJSONString(wxConfig));
			logger.info("wxconfig============" + JSON.toJSON(wxConfig));
            return wxConfig;
        } else {
            return null;
        }
//		}else{
//			return null;
//		}
	}

	//分享增加次数操作
	public void addCount(String openid,HttpServletResponse resp) {
		ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            if (jedis.get(SHARE_KEY + openid).equals("true")) {
                logger.info("好友分享操作，次数+1:" + openid);
                int count = Integer.parseInt(jedis.get(COUNT_KEY + openid));
                jedis.set(COUNT_KEY + openid, String.valueOf(count - 1));
                jedis.set(SHARE_KEY + openid, "false");
				resp.getWriter().write(new Boolean(true).toString());
            } else {
                logger.info("好友分享操作，次数已经+1：" + openid);
				resp.getWriter().write(new Boolean(false).toString());
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }finally {
            jedis.close();
        }
    }

	//分享增加次数操作,盆友圈
	public void addCountCircle(String fromOpenid,HttpServletResponse resp) {
		ShardedJedis jedis = null;
		try{
            jedis = shardedJedisPool.getResource();
            if (jedis.get(SHARE_KEY + fromOpenid).equals("true")) {
                logger.info("朋友圈分享操作，次数+1:" + fromOpenid);
                int count = Integer.parseInt(jedis.get(COUNT_KEY + fromOpenid));
                jedis.set(COUNT_KEY + fromOpenid, String.valueOf(count - 1));
                jedis.set(SHARE_KEY + fromOpenid, "false");
				resp.getWriter().write(new Boolean(true).toString());
            } else {
                logger.info("朋友圈分享操作，次数已经+1：" + fromOpenid);
				resp.getWriter().write(new Boolean(false).toString());
            }
		}catch (Exception e){
			logger.info(e.getMessage());
		}finally {
			jedis.close();
		}
	}


}
