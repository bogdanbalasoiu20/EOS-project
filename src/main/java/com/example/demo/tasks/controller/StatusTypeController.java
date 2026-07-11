package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.StatusTypeDto;
import com.example.demo.tasks.dto.request.StatusType.CreateStatusTypeRequest;
import com.example.demo.tasks.dto.request.StatusType.UpdateStatusTypeRequest;
import com.example.demo.tasks.dto.response.StatusType.StatusTypeResponse;
import com.example.demo.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@RequiredArgsConstructor
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    //testat, merge
    @GetMapping
    public ResponseEntity<List<StatusTypeResponse>> getAllStatuses() {
        return ResponseEntity.ok(statusTypeService.getAllStatuses());
    }

    //testat, merge
    @GetMapping("/{statusId}")
    public ResponseEntity<StatusTypeResponse> getStatusById(@PathVariable String statusId) {
        return ResponseEntity.ok(statusTypeService.getStatusById(statusId));
    }

    //testat, merge
    @PostMapping
    public ResponseEntity<StatusTypeResponse> createStatus(@Valid @RequestBody CreateStatusTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statusTypeService.createStatus(request));
    }

    //testat, merge
    @PatchMapping("/{statusId}")
    public ResponseEntity<StatusTypeResponse> updateStatus(@PathVariable String statusId, @RequestBody UpdateStatusTypeRequest request) {
        return ResponseEntity.ok(statusTypeService.updateStatus(statusId, request));
    }

    //testat, merge
    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteStatus(@PathVariable String statusId) {
        statusTypeService.deleteStatus(statusId);
        return ResponseEntity.noContent().build();
    }

}
