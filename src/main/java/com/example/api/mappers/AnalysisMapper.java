package com.example.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.entities.Analysis;
@Mapper(componentModel = "spring", uses = WeedMapper.class)
public interface AnalysisMapper {

    RecoveryAnalysisDto recoveryAnalysisToDto(Analysis analysis);

    List<RecoveryAnalysisDto> recoveryAnalysiesDto(List<Analysis> analysies);

}
