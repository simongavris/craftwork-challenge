package com.simongavris.taskmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    //fetch all tasks(ordered)
    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    //fetch a single task
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable(value = "id") UUID  id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    //create a single task
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task){
        return taskRepository.save(task);
    }

    //update a single task
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable(value = "id") UUID id, @Valid @RequestBody Task task){
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());

        return taskRepository.save(updatedTask);
    }

    //delete a single task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") UUID id){
        taskRepository.delete(taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException(id)));

        return ResponseEntity.ok().build();
    }

}
