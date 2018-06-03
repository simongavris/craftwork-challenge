package com.simongavris.taskmanagement.util;

public enum Status {
    OPEN(3, "Open"),
    WIP(2, "Work in Progress"),
    DONE(1, "Done");



    private int value;
    private String status;

    Status(int value, String status){
        this.value = value;
        this.status = status;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){return status;}


}
