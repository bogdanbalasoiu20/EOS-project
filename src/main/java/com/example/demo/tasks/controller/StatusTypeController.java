package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.StatusTypeDto;
import com.example.demo.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    public StatusTypeController(StatusTypeService statusTypeService) {
        this.statusTypeService = statusTypeService;
    }

    @GetMapping
    public List<StatusTypeDto> getAllStatuses(){
        return statusTypeService.getAllStatuses();
    }

    @PostMapping
    public StatusTypeDto createStatus(@RequestBody @Valid StatusTypeDto statusTypeDto){
        return statusTypeService.createStatus(statusTypeDto);
    }
}
