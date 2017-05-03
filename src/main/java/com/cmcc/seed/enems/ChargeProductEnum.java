package com.cmcc.seed.enems;

/**
 * Created by cmcc on 16/1/19.
 */
public enum ChargeProductEnum {
    FLOW_2(2D,10001),
    FLOW_10(10D,10002),
    FLOW_30(30D,10003),
    FLOW_500(500D,10004),;

    private Double flow;

    private Integer gid;

    ChargeProductEnum(Double flow, Integer gid) {
        this.flow = flow;
        this.gid = gid;
    }

    public static Double getFlow(int gid) {
        for (ChargeProductEnum product : ChargeProductEnum.values()) {
            if (product.getGid() == gid) {
                return product.flow;
            }
        }
        return null;
    }

    public static Integer getGid(double flow) {
        for (ChargeProductEnum product : ChargeProductEnum.values()) {
            if (product.getFlow() == flow) {
                return product.getGid();
            }
        }
        return null;
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
}
