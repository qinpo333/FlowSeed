package com.cmcc.seed.enems;

/**
 * 操作类型
 * Created by cmcc on 16/1/16.
 */
public enum OpType {
    WATER(1, "浇水"),
    HAVEST(2, "收获"),
    PLANT(3, "种植"),
    EXCHANGE(4, "兑换"),
    GIVE(5, "赠送"),
    UPROOT(6, "铲除"),
    RECEIVE(7, "领取"),
    SHARE(8, "分享"),
    STEAL(9, "偷取"),
    WATER_FRIEND(10, "帮好友浇水"),
    GIVE_EXPIRE(11, "赠送过期");

    private int value;

    private String name;

    OpType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(int value) {
        for (OpType opType : OpType.values()) {
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
