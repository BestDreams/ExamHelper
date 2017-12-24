package com.svse.dream.bean;

import java.io.Serializable;

/**
 * Created by Dream on 2016/12/2.
 * 创建题目实体
 */

public class QuestionMyLib extends Question implements Serializable {

    private int id;

    public QuestionMyLib() {

    }

    public QuestionMyLib(int id, int question_ID, String osName, String question_content, String question_answerA, String question_answerB, String question_answerC, String question_answerD, String question_explain, int question_answer1, int question_answer2, int question_answer3, int question_answer4) {
        this.id = id;
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
