package com.simongavris.taskmanagement.model;

import com.simongavris.taskmanagement.persistence.TaskRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TaskQueue extends LinkedList<Task> {

    private static TaskQueue INSTANCE;


    /**
     * TaskQueue is a Singleton extension of LinkedList. There should always exist only one Queue
     */
    private TaskQueue(){
        super();
    }

    /**
     * Instantiates an Instance if not happend yet and returns it.
     * @return {@code INSTANCE}.
     */
    public static TaskQueue getInstance(){
        return (INSTANCE == null)? INSTANCE = new TaskQueue() : INSTANCE;
    }

    /**
     * Searching the Queue for Task with given UUID and removes it using the super method.
     * @param id UUID of task to be removed
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(UUID id) {
        for(Task t : this){
            if(t.getId().equals(id))
                return super.remove(t);
        }
        return false;
    }
}
