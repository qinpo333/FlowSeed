package com.cmcc.seed.enems;

/**
 * Created by cmcc on 16/1/15.
 */
public enum LandStatus {
    WATER(0, "可浇水"),
    SHOVEL(2, "可铲除"),
    HARVEST(1, "可收获"),
    NORMAL(-1, "正常"),
    PLANT(3,"可种植");

    private int value;
    private String title;

    LandStatus(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static String getTitle(int value) {
        for (LandStatus level : LandStatus.values()) {
            if (level.getValue() == value) {
                return level.title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
