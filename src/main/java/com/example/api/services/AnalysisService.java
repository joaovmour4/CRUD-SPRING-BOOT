package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.CreateAnalysisDto;
import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.entities.Analysis;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.AnalysisMapper;
import com.example.api.repositories.AnalysisRepository;

@Service
public class AnalysisService {
    
    @Autowired 
    private AnalysisRepository analysisRepository;

    @Autowired 
    private AnalysisMapper analysisMapper;

    public RecoveryAnalysisDto createAnalysis(CreateAnalysisDto createAnalysisDto){
        Analysis analysis = Analysis.builder()
                                    .idUser(createAnalysisDto.idUser())
                                    .result(createAnalysisDto.result())
                                    .analysis_date(createAnalysisDto.analysis_date())
                                    .build();

        Analysis analysisSaved = analysisRepository.save(analysis);

        return analysisMapper.recoveryAnalysisToDto(analysisSaved);
    }

    public RecoveryAnalysisDto getAnalysisById(Long id){
        Analysis analysis = analysisRepository.findById(id)
                                                .orElseThrow(()-> new ResourceNotFoundException("Análise não encontrada"));
                                    
        return analysisMapper.recoveryAnalysisToDto(analysis);
    }

}
