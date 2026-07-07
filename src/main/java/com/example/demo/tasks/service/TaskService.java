package com.example.demo.tasks.service;

import com.example.demo.tasks.dto.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();

    public List<TaskDTO> getTasks() {
        log.info("Getting tasks: ");
        return tasks;
    }

    public List<TaskDTO> addTask(TaskDTO task) {
        TaskDTO buildTask = buildTask(task);

        tasks.add(buildTask);
        log.info("Added task: " + buildTask);

        return tasks;
    }

    public TaskDTO buildTask(TaskDTO task) {
        return TaskDTO.builder()
                .id(task.getId())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .build();
    }

    public void deleteTask(Long taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

    public TaskDTO patchTask(Long taskId, TaskDTO updatedTask) {
        for (TaskDTO task : tasks) {
            if (task.getId().equals(taskId)) {

                if (updatedTask.getContent() != null) {
                    task.setContent(updatedTask.getContent());
                }

                if (updatedTask.getDueDate() != null) {
                    task.setDueDate(updatedTask.getDueDate());
                }

                if (updatedTask.getStatus() != null) {
                    task.setStatus(updatedTask.getStatus());
                }

                return task;
            }
        }

        return null;
    }

    public TaskDTO getTaskById(Long taskId) {
        for(TaskDTO task : tasks){
            if(task.getId().equals(taskId)){
                return task;
            }
        }
        return null;
    }

}
