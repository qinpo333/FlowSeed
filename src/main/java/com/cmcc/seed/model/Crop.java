package com.cmcc.seed.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Crop implements Serializable{

    private static final long serialVersionUID = 3052910459192285686L;

    private Long id;

    private String name;

    private String desc;

    private Integer seedLevel;

    private Integer seedPrice;

    private String seedImg;

    private Double expectYield;

    private Integer waterTimes;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getSeedLevel() {
        return seedLevel;
    }

    public void setSeedLevel(Integer seedLevel) {
        this.seedLevel = seedLevel;
    }

    public Integer getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(Integer seedPrice) {
        this.seedPrice = seedPrice;
    }

    public String getSeedImg() {
        return seedImg;
    }

    public void setSeedImg(String seedImg) {
        this.seedImg = seedImg;
    }

    public Double getExpectYield() {
        return expectYield;
    }

    public void setExpectYield(Double expectYield) {
        this.expectYield = expectYield;
    }

    public Integer getWaterTimes() {
        return waterTimes;
    }

    public void setWaterTimes(Integer waterTimes) {
        this.waterTimes = waterTimes;
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

    public static void main(String[] args) throws Exception{
        //当前日期
        Calendar aCalendar = Calendar.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2016-05-27");
        aCalendar.setTime(date);
        //成熟日期
        Calendar bCalendar = Calendar.getInstance();
        Date date1 = dateFormat.parse("2016-06-03");
        bCalendar.setTime(date1);

        int days = 0;
        while(aCalendar.before(bCalendar)){
            days++;
            aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        System.out.print(days);
    }
}