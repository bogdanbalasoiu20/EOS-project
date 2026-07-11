package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.dto.mapper.StatusTypeMapper;
import com.example.demo.tasks.dto.request.StatusType.CreateStatusTypeRequest;
import com.example.demo.tasks.dto.request.StatusType.UpdateStatusTypeRequest;
import com.example.demo.tasks.dto.response.StatusType.StatusTypeResponse;
import com.example.demo.tasks.exception.StatusTypeAlreadyExistsException;
import com.example.demo.tasks.exception.StatusTypeNotFoundException;
import com.example.demo.tasks.repository.StatusTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeService {
    private final StatusTypeRepository statusTypeRepository;
    private final StatusTypeMapper mapper;

    public List<StatusTypeResponse> getAllStatuses() {
        log.info("Retrieving all status types");

        return statusTypeRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public StatusTypeResponse getStatusById(String id) {
        return mapper.toResponse(findStatus(id));
    }

    public StatusTypeResponse createStatus(CreateStatusTypeRequest request) {
        if (statusTypeRepository.existsByStatusName(request.statusName())) {
            throw new StatusTypeAlreadyExistsException(request.statusName());
        }

        StatusType status = mapper.toEntity(request);

        return mapper.toResponse(statusTypeRepository.save(status));
    }

    public StatusTypeResponse updateStatus(String id, UpdateStatusTypeRequest request) {
        StatusType status = findStatus(id);

        if (request.statusName() != null) {
            status.setStatusName(request.statusName());
        }

        return mapper.toResponse(statusTypeRepository.save(status));
    }

    public void deleteStatus(String id) {
        statusTypeRepository.delete(findStatus(id));
    }

    private StatusType findStatus(String id) {
        return statusTypeRepository.findById(id).orElseThrow(() -> new StatusTypeNotFoundException(id));
    }

}
