package com.cmcc.seed.mall_cp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PresentReq {
    private String transNo;// 流水号，需要确保唯一性
    private Integer asCP;// 商户编号
    private String tel;// 充值目标用户手机号
    private Integer gid;// 商品编号

    public boolean isValid() {
        return transNo != null && transNo.length() <= 32
                && isValidTelphone(tel) && gid != null;
    }

    private static final Pattern TELPHONE_PATTERN = Pattern
            .compile("^1\\d{10}");

    public static boolean isValidTelphone(String telphone) {
        if (telphone == null) {
            return false;
        }
        telphone = telphone.trim();
        if (telphone.length() != 11) {
            return false;
        }
        Matcher m = TELPHONE_PATTERN.matcher(telphone);
        return m.matches();
    }

    public String getTransNo() {
        return transNo;
    }

    public Integer getAsCP() {
        return asCP;
    }

    public String getTel() {
        return tel;
    }

    public Integer getGid() {
        return gid;
    }

    public PresentReq setTransNo(String transNo) {
        this.transNo = transNo;
        return this;
    }

    public PresentReq setAsCP(Integer asCP) {
        this.asCP = asCP;
        return this;
    }

    public PresentReq setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public PresentReq setGid(Integer gid) {
        this.gid = gid;
        return this;
    }

}