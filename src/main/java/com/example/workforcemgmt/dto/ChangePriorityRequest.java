package com.example.workforcemgmt.dto;

import com.example.workforcemgmt.model.Priority;
import lombok.Data;

@Data
public class ChangePriorityRequest {
    private Priority priority;
}