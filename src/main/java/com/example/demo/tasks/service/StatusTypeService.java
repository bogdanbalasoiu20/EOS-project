package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.dto.StatusTypeDto;
import com.example.demo.tasks.dto.mapper.StatusTypeMapper;
import com.example.demo.tasks.repository.StatusTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeService {
    private final StatusTypeRepository statusTypeRepository;
    private final StatusTypeMapper statusMapper;

    public List<StatusTypeDto> getAllStatuses(){
        log.info("Statuses retrieved");
        return statusTypeRepository.findAll()
                .stream()
                .map(statusMapper::toDto)
                .toList();
    }

    public StatusTypeDto createStatus(@Valid StatusTypeDto statusTypeDto){
        log.info("status created");
        StatusType status = statusMapper.toEntity(statusTypeDto);
        StatusType savedStatus = statusTypeRepository.save(status);
        return statusMapper.toDto(savedStatus);
    }
}
