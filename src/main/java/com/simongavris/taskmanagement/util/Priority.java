package com.simongavris.taskmanagement.util;

public enum Priority {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private int value;
    public int getValue(){
        return value;
    }
    Priority(int value){
        this.value = value;
    }

}
