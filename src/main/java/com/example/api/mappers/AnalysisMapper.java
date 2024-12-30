package com.example.api.mappers;

import org.mapstruct.Mapper;

import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.entities.Analysis;
@Mapper(componentModel = "spring")
public interface AnalysisMapper {

    RecoveryAnalysisDto recoveryAnalysisToDto(Analysis analysis);

}
