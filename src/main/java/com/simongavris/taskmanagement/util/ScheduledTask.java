package com.simongavris.taskmanagement.util;

import com.simongavris.taskmanagement.model.Task;
import com.simongavris.taskmanagement.model.TaskQueue;
import com.simongavris.taskmanagement.persistence.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private TaskRepository taskRepository;

    //random.nextInt(max - min + 1) + min

    /**
     * Create a random Task and talk about it
     * Task gets created in a Random interval that is set by the application.properties
     */
    @Scheduled(fixedDelayString = "${my.random.delay}")
    public void createRandomTask() {
        Task t = new Task("Random-Task-" + dateFormat.format(new Date()), Priority.MEDIUM, Status.OPEN, "This is a randomly created Task");
        taskRepository.save(t);
        TaskQueue.getInstance().add(t);
        log.info("Creating Random Task at {}", dateFormat.format(new Date()));
    }
}
