package com.cmcc.seed.model;

import java.io.Serializable;
import java.util.Date;

public class Charge implements Serializable{

    private static final long serialVersionUID = -3375627149800464212L;

    private Long id;

    private String transNo;

    private Long userId;

    private String openId;

    private String phone;

    private Double flow;

    private Integer gid;

    private Long rechrNo;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Long getRechrNo() {
        return rechrNo;
    }

    public void setRechrNo(Long rechrNo) {
        this.rechrNo = rechrNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}