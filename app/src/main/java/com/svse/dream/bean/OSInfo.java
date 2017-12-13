package com.svse.dream.bean;

/**
 * Created by Hello on 2017/6/11.
 */
public class OSInfo {
    private String osName;
    private int count;

    public OSInfo(String osName, int count) {
        this.osName = osName;
        this.count = count;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
