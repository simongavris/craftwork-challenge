package com.simongavris.taskmanagement.controller;

import com.simongavris.taskmanagement.model.Task;
import com.simongavris.taskmanagement.model.TaskQueue;
import com.simongavris.taskmanagement.model.dto.TaskDto;
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
    @GetMapping("/tasks/{id}")
    public TaskDto getTaskById(@PathVariable(value = "id") UUID  id){
        return convertToDto(taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    //create a single task
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto){
        return convertToDto(taskRepository.save(convertToEntity(taskDto)));
    }

    //update a single task
    @PutMapping("/tasks/{id}")
    public TaskDto updateTask(@PathVariable(value = "id") UUID id, @Valid @RequestBody TaskDto taskDto){
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if(taskDto.getTitle() != null)
            updatedTask.setTitle(taskDto.getTitle());
        if(taskDto.getDescription() != null)
            updatedTask.setDescription(taskDto.getDescription());
        if(taskDto.getPriority() != null)
            updatedTask.setPriority(taskDto.getPriority());
        if(taskDto.getStatus() != null)
            updatedTask.setStatus(taskDto.getStatus());
         return convertToDto(taskRepository.save(updatedTask));
    }

    //delete a single task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") UUID id){
        Task t = taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException(id));
        TaskQueue.getInstance().remove(t.getId());
        taskRepository.delete(t);
        return ResponseEntity.ok().build();
    }

    public void prepeareQueue(){
        try {
            TaskQueue.getInstance().addAll(taskRepository.findAll(new Sort(Sort.Direction.ASC, "queueId")));
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
