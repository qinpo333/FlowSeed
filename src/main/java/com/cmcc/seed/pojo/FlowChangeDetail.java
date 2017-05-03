package com.cmcc.seed.pojo;

import java.util.Date;

/**
 * 流量收支明细
 * Created by cmcc on 16/1/19.
 */
public class FlowChangeDetail {

    private String opType;

    private String phone;

    private String opTime;

    private double flowChange;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public double getFlowChange() {
        return flowChange;
    }

    public void setFlowChange(double flowChange) {
        this.flowChange = flowChange;
    }
}
