package com.simongavris.taskmanagement.util;

import com.simongavris.taskmanagement.model.TaskQueue;
import com.simongavris.taskmanagement.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * The DataLoader has the job of filling the Queue with the Data from the Database
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(ApplicationArguments args) {
        TaskQueue.getInstance(taskRepository.findAll(new Sort(Sort.Direction.ASC, "id")));
    }
}
