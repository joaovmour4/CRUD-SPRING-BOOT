package com.example.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.CreateAnalysisDto;
import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.dto.RecoveryWeedDto;
import com.example.api.entities.Analysis;
import com.example.api.entities.Weed;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.AnalysisMapper;
import com.example.api.repositories.AnalysisRepository;

@Service
public class AnalysisService {
    
    @Autowired 
    private AnalysisRepository analysisRepository;

    @Autowired
    private WeedService weedService;

    @Autowired 
    private AnalysisMapper analysisMapper;

    public RecoveryAnalysisDto createAnalysis(CreateAnalysisDto createAnalysisDto){

        List<Weed> weeds = weedService.getWeedsByArrayId(createAnalysisDto.weeds());

        Analysis analysis = Analysis.builder()
                                    .idUser(createAnalysisDto.idUser())
                                    .result(createAnalysisDto.result())
                                    .analysis_date(createAnalysisDto.analysis_date())
                                    .build();

        analysisRepository.save(analysis);

        analysis.setWeeds(weeds);

        return analysisMapper.recoveryAnalysisToDto(analysisRepository.save(analysis));
    }

    public RecoveryAnalysisDto getAnalysisById(Long id){
        Analysis analysis = analysisRepository.findById(id)
                                                .orElseThrow(()-> new ResourceNotFoundException("Análise não encontrada"));
                                    
        return analysisMapper.recoveryAnalysisToDto(analysis);
    }

}
