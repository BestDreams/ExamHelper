package com.svse.dream.bean;

/**
 * Created by Dream on 2016/12/2.
 * 创建更新记录实体
 */

public class Version {
    private int id;
    private String version;
    private String date;
    private String content;
    private String bug;

    public Version() {
    }

    public Version(int id, String version, String date, String content, String bug) {
        this.id = id;
        this.version = version;
        this.date = date;
        this.content = content;
        this.bug = bug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }
}
