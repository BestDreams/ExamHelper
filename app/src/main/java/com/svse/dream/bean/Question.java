package com.svse.dream.bean;

import java.io.Serializable;

/**
 * Created by Dream on 2016/12/2.
 * 创建题目实体
 */

public class Question implements Serializable {
    protected int question_ID;
    protected String osName;
    protected String question_content;
    protected String question_answerA;
    protected String question_answerB;
    protected String question_answerC;
    protected String question_answerD;
    protected String question_explain;
    protected int question_answer1;
    protected int question_answer2;
    protected int question_answer3;
    protected int question_answer4;
    protected int question_select1;
    protected int question_select2;
    protected int question_select3;
    protected int question_select4;

    public Question() {
    }

    public Question(int question_ID, String osName, String question_content, String question_answerA, String question_answerB, String question_answerC, String question_answerD, String question_explain, int question_answer1, int question_answer2, int question_answer3, int question_answer4) {
        this.question_ID = question_ID;
        this.osName = osName;
        this.question_content = question_content;
        this.question_answerA = question_answerA;
        this.question_answerB = question_answerB;
        this.question_answerC = question_answerC;
        this.question_answerD = question_answerD;
        this.question_explain = question_explain;
        this.question_answer1 = question_answer1;
        this.question_answer2 = question_answer2;
        this.question_answer3 = question_answer3;
        this.question_answer4 = question_answer4;
        this.question_select1 = -1;
        this.question_select2 = -1;
        this.question_select3 = -1;
        this.question_select4 = -1;
    }

    public int getQuestion_select4() {
        return question_select4;
    }

    public void setQuestion_select4(int question_select4) {
        this.question_select4 = question_select4;
    }

    public int getQuestion_ID() {
        return question_ID;
    }

    public void setQuestion_ID(int question_ID) {
        this.question_ID = question_ID;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getQuestion_answerA() {
        return question_answerA;
    }

    public void setQuestion_answerA(String question_answerA) {
        this.question_answerA = question_answerA;
    }

    public String getQuestion_answerB() {
        return question_answerB;
    }

    public void setQuestion_answerB(String question_answerB) {
        this.question_answerB = question_answerB;
    }

    public String getQuestion_answerC() {
        return question_answerC;
    }

    public void setQuestion_answerC(String question_answerC) {
        this.question_answerC = question_answerC;
    }

    public String getQuestion_answerD() {
        return question_answerD;
    }

    public void setQuestion_answerD(String question_answerD) {
        this.question_answerD = question_answerD;
    }

    public String getQuestion_explain() {
        return question_explain;
    }

    public void setQuestion_explain(String question_explain) {
        this.question_explain = question_explain;
    }

    public int getQuestion_answer1() {
        return question_answer1;
    }

    public void setQuestion_answer1(int question_answer1) {
        this.question_answer1 = question_answer1;
    }

    public int getQuestion_answer2() {
        return question_answer2;
    }

    public void setQuestion_answer2(int question_answer2) {
        this.question_answer2 = question_answer2;
    }

    public int getQuestion_answer3() {
        return question_answer3;
    }

    public void setQuestion_answer3(int question_answer3) {
        this.question_answer3 = question_answer3;
    }

    public int getQuestion_answer4() {
        return question_answer4;
    }

    public void setQuestion_answer4(int question_answer4) {
        this.question_answer4 = question_answer4;
    }

    public int getQuestion_select1() {
        return question_select1;
    }

    public void setQuestion_select1(int question_select1) {
        this.question_select1 = question_select1;
    }

    public int getQuestion_select2() {
        return question_select2;
    }

    public void setQuestion_select2(int question_select2) {
        this.question_select2 = question_select2;
    }

    public int getQuestion_select3() {
        return question_select3;
    }

    public void setQuestion_select3(int question_select3) {
        this.question_select3 = question_select3;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_ID=" + question_ID +
                ", osName='" + osName + '\'' +
                ", question_content='" + question_content + '\'' +
                ", question_answerA='" + question_answerA + '\'' +
                ", question_answerB='" + question_answerB + '\'' +
                ", question_answerC='" + question_answerC + '\'' +
                ", question_answerD='" + question_answerD + '\'' +
                ", question_explain='" + question_explain + '\'' +
                ", question_answer1=" + question_answer1 +
                ", question_answer2=" + question_answer2 +
                ", question_answer3=" + question_answer3 +
                ", question_answer4=" + question_answer4 +
                ", question_select1=" + question_select1 +
                ", question_select2=" + question_select2 +
                ", question_select3=" + question_select3 +
                ", question_select4=" + question_select4 +
                '}';
    }
}
