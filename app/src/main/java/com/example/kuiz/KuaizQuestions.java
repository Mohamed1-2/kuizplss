package com.example.kuiz;

import android.app.Activity;

public class KuaizQuestions extends Activity {
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;

    public KuaizQuestions(String ques, String opa, String opb, String opc, String opd, String ans) {

        question = ques;
        optionA = opa;
        optionB = opb;
        optionC = opc;
        optionD = opd;
        answer = ans;
    }
    public KuaizQuestions() {
        id = 0;
        question = "";
        optionA = "";
        optionB = "";
        optionC = "";
        optionD = "";
        answer = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
