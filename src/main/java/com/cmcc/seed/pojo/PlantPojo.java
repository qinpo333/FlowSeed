package com.cmcc.seed.pojo;

import com.cmcc.seed.model.Land;

import java.io.Serializable;

/**
 * Created by cmcc on 16/1/15.
 */
public class PlantPojo implements Serializable {

    private static final long serialVersionUID = 2427403997342021969L;
    /**
     * 唯一标识ID
     */
    private long id;

    /**
     * 土地编号
     */
    private int ground;

    /**
     * 状态
     * 可浇水/铲除/收获/正常
     * 0/1/2/-1
     */
    private int status;

    /**
     * 生长状态
     * 0、1、2、3、4、5
     */
    private int growthStatus;

    /**
     * 预期收获量
     */
    private double flow;

    /**
     * 成熟需要天数
     */
    private int matureDays;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGround() {
        return ground;
    }

    public void setGround(int ground) {
        this.ground = ground;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGrowthStatus() {
        return growthStatus;
    }

    public void setGrowthStatus(int growthStatus) {
        this.growthStatus = growthStatus;
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public int getMatureDays() {
        return matureDays;
    }

    public void setMatureDays(int matureDays) {
        this.matureDays = matureDays;
    }
}
