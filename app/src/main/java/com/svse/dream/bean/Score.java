package com.svse.dream.bean;


import java.util.Date;

/**
 * Created by Hello on 2017/6/16.
 */
public class Score {
    private int score;
    private int NoCount;
    private int aErrorCount;
    private int aSuccesssCount;
    private Date EndTime;
    private String totalTime;
    private String sureScore;

    public Score() {
    }

    public Score(int score, int noCount, int aErrorCount, int aSuccesssCount, Date endTime, String totalTime, String sureScore) {
        this.score = score;
        NoCount = noCount;
        this.aErrorCount = aErrorCount;
        this.aSuccesssCount = aSuccesssCount;
        EndTime = endTime;
        this.totalTime = totalTime;
        this.sureScore = sureScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNoCount() {
        return NoCount;
    }

    public void setNoCount(int noCount) {
        NoCount = noCount;
    }

    public int getaErrorCount() {
        return aErrorCount;
    }

    public void setaErrorCount(int aErrorCount) {
        this.aErrorCount = aErrorCount;
    }

    public int getaSuccesssCount() {
        return aSuccesssCount;
    }

    public void setaSuccesssCount(int aSuccesssCount) {
        this.aSuccesssCount = aSuccesssCount;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getSureScore() {
        return sureScore;
    }

    public void setSureScore(String sureScore) {
        this.sureScore = sureScore;
    }
}
