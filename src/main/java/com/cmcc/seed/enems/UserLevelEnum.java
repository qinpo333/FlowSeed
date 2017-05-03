package com.cmcc.seed.enems;

/**
 * Created by cmcc on 16/1/15.
 */
public enum UserLevelEnum {
    NEW(1, "种植新手"),
    SECOND(2, "初学乍练"),
    THIRD(3, "勉勉强强");

    private int value;
    private String title;

    UserLevelEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static String getTitle(int value) {
        for (UserLevelEnum level : UserLevelEnum.values()) {
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
