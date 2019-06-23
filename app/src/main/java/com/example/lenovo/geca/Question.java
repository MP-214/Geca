package com.example.lenovo.geca;

import java.util.ArrayList;

/**
 * Created by User on 7/30/2017.
 */

public class Question {
    String name,message,time,type,account;
    int year;
    String key;
    ArrayList<Answer> answers=new ArrayList<>();

    public Question()
    {

    }

    public Question(String account, String message, String name, String time, String type, int year, String key, ArrayList<Answer> answers) {
        this.account = account;
        this.message = message;
        this.name = name;
        this.time = time;
        this.type = type;
        this.year = year;
        this.key=key;
        this.answers=answers;
        /*this.answers.add(new Answer("student","Answer1","Rohitwa","10:51","answer",3));
        this.answers.add(new Answer("student","Answer2","Ssnk","10:50","answer",3));*/
    }

    public String getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
