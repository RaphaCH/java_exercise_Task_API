package com.task.task.model;


public enum ErrorConstant {

    TEC001("TEC001","technical error in task api"),
    FUNC001("FUNC001","functional error in task api");

    public final String key;
    public final String value;  

    ErrorConstant(String key, String value) {
        this.key = key;
        this.value = value;
    }
}










