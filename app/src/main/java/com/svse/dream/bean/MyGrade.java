package com.svse.dream.bean;

/**
 * Created by ZhangPing on 2017/12/25.
 */

public class MyGrade {
    private Integer id;
    private String osNames;
    private Integer grade;
    private Integer submitNo;
    private Integer submitYes;
    private Integer submitError;
    private Integer submitCorrect;
    private String startTime;
    private String endTime;
    private String totalTime;
    private String correctProcent;
    private Integer totalNum;

    public MyGrade() {
    }

    public MyGrade(Integer id, String osNames, Integer grade, Integer submitNo, Integer submitYes,
                   Integer submitError, Integer submitCorrect, String startTime, String endTime,
                   String totalTime, String correctProcent, Integer totalNum) {
        this.id = id;
        this.osNames = osNames;
        this.grade = grade;
        this.submitNo = submitNo;
        this.submitYes = submitYes;
        this.submitError = submitError;
        this.submitCorrect = submitCorrect;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.correctProcent = correctProcent;
        this.totalNum = totalNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOsNames() {
        return osNames;
    }

    public void setOsNames(String osNames) {
        this.osNames = osNames;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSubmitNo() {
        return submitNo;
    }

    public void setSubmitNo(Integer submitNo) {
        this.submitNo = submitNo;
    }

    public Integer getSubmitYes() {
        return submitYes;
    }

    public void setSubmitYes(Integer submitYes) {
        this.submitYes = submitYes;
    }

    public Integer getSubmitError() {
        return submitError;
    }

    public void setSubmitError(Integer submitError) {
        this.submitError = submitError;
    }

    public Integer getSubmitCorrect() {
        return submitCorrect;
    }

    public void setSubmitCorrect(Integer submitCorrect) {
        this.submitCorrect = submitCorrect;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getCorrectProcent() {
        return correctProcent;
    }

    public void setCorrectProcent(String correctProcent) {
        this.correctProcent = correctProcent;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalName(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
