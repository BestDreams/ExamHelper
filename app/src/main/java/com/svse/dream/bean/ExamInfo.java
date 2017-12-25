package com.svse.dream.bean;

/**
 * Created by ZhangPing on 2017/12/25.
 */

public class ExamInfo {
    private int iconId;
    private String title;
    private String text;

    public ExamInfo() {
    }

    public ExamInfo(int iconId, String title, String text) {
        this.iconId = iconId;
        this.title = title;
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
