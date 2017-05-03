package com.cmcc.seed.enems;

/**
 * 操作类型
 * Created by cmcc on 16/1/16.
 */
public enum GiveStatus {
    GIVE(0, "赠送中"),
    RECEIVE(1, "已领取"),
    EXPIRE(2, "过期");

    private int value;

    private String name;

    GiveStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(int value) {
        for (GiveStatus opType : GiveStatus.values()) {
            if (opType.getValue() == value) {
                return opType.name;
            }
        }
        return "";
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
