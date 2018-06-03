package com.simongavris.taskmanagement.controller;

import com.simongavris.taskmanagement.model.Task;
import com.simongavris.taskmanagement.model.TaskQueue;
import com.simongavris.taskmanagement.model.dto.TaskDto;
import com.simongavris.taskmanagement.util.Priority;
import com.simongavris.taskmanagement.util.Status;
import com.simongavris.taskmanagement.util.TaskNotFoundException;
import com.simongavris.taskmanagement.persistence.TaskRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private ModelMapper modelMapper = new ModelMapper();



    //fetch all tasks(ordered)
    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks(){
        //TaskQueue.getInstance().addAll(taskRepository.findAll(new Sort(Sort.Direction.ASC, "queueId")));
        List<Task> tasks = taskRepository.findAll();//TaskQueue.getInstance();
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    //fetch a single task
    @GetMapping("tasks/{uuid}")
    public TaskDto getTaskByUuid(@PathVariable(value ="uuid") UUID uuid){
        return convertToDto(taskRepository.findByUuid(uuid).orElseThrow(() -> new TaskNotFoundException(uuid)));
    }


    //create a single task
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto){

        //Set default values
        if(taskDto.getPriority() == null)
            taskDto.setPriority(Priority.MEDIUM);
        if(taskDto.getStatus() == null)
            taskDto.setStatus(Status.OPEN);
        taskDto.setUuid(UUID.randomUUID());


        return convertToDto(taskRepository.save(convertToEntity(taskDto)));
    }


    //update a single task
    @PutMapping("/tasks/{uuid}")
    public TaskDto updateTask(@PathVariable(value = "uuid") UUID uuid, @Valid @RequestBody TaskDto taskDto){
        Task t = taskRepository.findByUuid(uuid).orElseThrow(() -> new TaskNotFoundException(uuid));

        //dont override values if not wanted
        if(taskDto.getTitle() != null)
            t.setTitle(taskDto.getTitle());
        if(taskDto.getDescription() != null)
            t.setDescription(taskDto.getDescription());
        if(taskDto.getPriority() != null)
            t.setPriority(taskDto.getPriority());
        if(taskDto.getStatus() != null)
            t.setStatus(taskDto.getStatus());
         return convertToDto(taskRepository.save(t));
    }

    //delete a single task
    @DeleteMapping("/tasks/{uuid}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "uuid") UUID uuid){
        Task t = taskRepository.findByUuid(uuid).orElseThrow(()-> new TaskNotFoundException(uuid));
        //TaskQueue.getInstance().remove(t.getUuid());
        taskRepository.delete(t);
        return ResponseEntity.ok().build();
    }

    public void prepeareQueue(){
        try {
            TaskQueue.getInstance().addAll(taskRepository.findAll(new Sort(Sort.Direction.ASC, "id")));
        }catch(NullPointerException e){
            //this means that the database is empty hence no data.
            e.printStackTrace();
        }
    }

    private TaskDto convertToDto(Task task){
        return modelMapper.map(task, TaskDto.class);
    }
    private Task convertToEntity(TaskDto taskDto){
        return modelMapper.map(taskDto, Task.class);
    }

}
