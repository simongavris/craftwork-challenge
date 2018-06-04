package com.simongavris.taskmanagement.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskQueue extends LinkedList<Task> {

    private static TaskQueue INSTANCE;
    private static Boolean initilized = false;


    /**
     * TaskQueue is a Singleton extension of LinkedList. There should always exist only one Queue
     */
    private TaskQueue(List<Task> tasks) {
        super(tasks);
    }

    private TaskQueue() {
        super();
    }

    /**
     * Instantiates an Instance if not happend yet and returns it.
     *
     * @return {@code INSTANCE}.
     */
    public static TaskQueue getInstance(List<Task> tasks) {
        return (INSTANCE == null) ? INSTANCE = new TaskQueue(tasks) : INSTANCE;
    }

    public static TaskQueue getInstance() {
        return (INSTANCE == null) ? INSTANCE = new TaskQueue() : INSTANCE;
    }


    /**
     * Searching the Queue for Task with given UUID and removes it using the super method.
     *
     * @param uuid UUID of task to be removed
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(UUID uuid) {
        if (uuid != null)
            for (Task t : this) {
                if (t.getUuid().equals(uuid))
                    return super.remove(t);
            }
        return false;
    }

    /**
     * Search Queue for Task by UUID
     *
     * @param uuid of the Task you are looking for
     * @return Optional for better NullPointer Handling
     */
    public Optional<Task> findByUuid(UUID uuid) {
        for (Task t : this) {
            if (t.getUuid().equals(uuid))
                return Optional.of(t);
        }
        return Optional.empty();
    }
}
