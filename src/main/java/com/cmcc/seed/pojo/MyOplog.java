package com.cmcc.seed.pojo;

/**
 * Created by cmcc on 16/3/2.
 */
public class MyOplog {

    private String OpUserOpenId;

    private String opUserNickName;

    private String opUserImgUrl;

    private String opTypeName;

    private String opTime;

    public String getOpUserOpenId() {
        return OpUserOpenId;
    }

    public void setOpUserOpenId(String opUserOpenId) {
        OpUserOpenId = opUserOpenId;
    }

    public String getOpUserNickName() {
        return opUserNickName;
    }

    public void setOpUserNickName(String opUserNickName) {
        this.opUserNickName = opUserNickName;
    }

    public String getOpUserImgUrl() {
        return opUserImgUrl;
    }

    public void setOpUserImgUrl(String opUserImgUrl) {
        this.opUserImgUrl = opUserImgUrl;
    }

    public String getOpTypeName() {
        return opTypeName;
    }

    public void setOpTypeName(String opTypeName) {
        this.opTypeName = opTypeName;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }
}
