package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.TaskDTO;
import com.example.demo.tasks.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    public List<TaskDTO> addTasks(@RequestBody TaskDTO task){
        return taskService.addTask(task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
    }

    @PatchMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody TaskDTO updatedTask){
        return taskService.patchTask(taskId, updatedTask);
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTaskById(@PathVariable Long taskId){
        return taskService.getTaskById(taskId);
    }
}
