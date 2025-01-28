package com.example.api.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.detection.DetectionService;
import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.dto.RecoveryDetectionDto;
import com.example.api.dto.UploadFileDto;
import com.example.api.entities.Analysis;
import com.example.api.entities.Image;
import com.example.api.entities.Weed;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.AnalysisMapper;
import com.example.api.repositories.AnalysisRepository;
import com.example.api.yolo.Detection;

import ai.onnxruntime.OrtException;

@Service
public class AnalysisService {
    
    @Autowired 
    private AnalysisRepository analysisRepository;

    @Autowired
    private WeedService weedService;

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private DetectionService detectionService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TokenService tokenService;

    public RecoveryAnalysisDto createAnalysis(MultipartFile file) throws OrtException, IOException {

        RecoveryDetectionDto result = detectionService.detection(file);
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = tokenService.getIdUserFromToken(authentication.toString());

        // List<Long> weedIds = new ArrayList<>();
        // weedIds.add(1L);
        // weedIds.add(2L);

        List<String> weedNames = result.detections().stream()
                           .map(d -> getDetectionName(d))
                           .toList();

        Set<Weed> weeds = weedService.getWeedsByArrayNames(weedNames);

        Analysis analysis = Analysis.builder()
                                    .idUser(Long.parseLong(id))
                                    .result(result.detections().size() > 0)
                                    .analysis_date(result.analysis_date())
                                    .weeds(weeds)
                                    .build();

                                    
        Analysis analysisSaved = analysisRepository.save(analysis);
        
        Image image = imageService.uploadImage(new UploadFileDto(analysis, result.image()));

        String thumbnailUrl = imageService.uploadThumbnail(new UploadFileDto(analysis, result.image()));

        analysisSaved.setThumbnail(thumbnailUrl);
        analysis.setImage(image);
        analysisRepository.save(analysisSaved);
        // imageService.saveImage(result.image(), analysisSaved.getId());  

        return analysisMapper.recoveryAnalysisToDto(analysisSaved);
    }

    public RecoveryAnalysisDto updateAnalysisName(Long id, String name){
        Analysis analysis = analysisRepository.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException("Análise não encontrada"));
        analysis.setName(name);

        Analysis analysisSaved = analysisRepository.save(analysis);

        return analysisMapper.recoveryAnalysisToDto(analysisSaved);
    }

    public Boolean removeAnalysisById(Long id){
        Analysis analysis = analysisRepository.findById(id)
                                              .orElseThrow(()-> new ResourceNotFoundException("Análise não encontrada"));
        analysisRepository.delete(analysis);
        return true;
    }

    public RecoveryAnalysisDto getAnalysisById(Long id){
        Analysis analysis = analysisRepository.findByIdWithResultWeeds(id)
                                                .orElseThrow(()-> new ResourceNotFoundException("Análise não encontrada"));
        return analysisMapper.recoveryAnalysisToDto(analysis);
    }

    public List<RecoveryAnalysisDto> getAnalysisByUser(long idUser){
        List<Analysis> analysies = analysisRepository.findAllByIdUser(idUser);

        return analysisMapper.recoveryAnalysiesDto(analysies);

    }

    ////////////////////// Functions //////////////////////
    private String getDetectionName(Detection detection){
        return detection.label();
    }

}
