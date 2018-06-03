package com.simongavris.taskmanagement.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

    private UUID id;

    public TaskNotFoundException(UUID id){
        super(String.format("Could not find Task with Id: " + id.toString()));
        this.id = id;
    }
    public UUID getTaskId(){
        return this.id;
    }
}
