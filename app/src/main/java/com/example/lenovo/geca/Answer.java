package com.example.lenovo.geca;

/**
 * Created by User on 7/30/2017.
 */

public class Answer {
    String name,message,time,type,account;
    int year;

    public Answer() {
    }

    public Answer(String account, String message, String name, String time, String type, int year) {
        this.account = account;
        this.message = message;
        this.name = name;
        this.time = time;
        this.type = type;
        this.year = year;
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
}
