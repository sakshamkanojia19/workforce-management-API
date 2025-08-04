package com.example.workforcemgmt.mapper;

import com.example.workforcemgmt.dto.TaskDto;
import com.example.workforcemgmt.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "assignee.name", target = "assigneeName")
    TaskDto toDto(Task task);
}