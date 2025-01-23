package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.dto.UpdateAnalysisNameDto;
import com.example.api.services.AnalysisService;

import ai.onnxruntime.OrtException;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    
    @Autowired
    private AnalysisService analysisService;

    @PostMapping()
    public ResponseEntity<RecoveryAnalysisDto> createAnalysis(@RequestParam("file") MultipartFile file) throws OrtException, IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RecoveryAnalysisDto analysis = analysisService.createAnalysis(file);
        return new ResponseEntity<>(analysis, HttpStatus.CREATED);
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<RecoveryAnalysisDto> putAnalysisName(@PathVariable Long id, @RequestBody UpdateAnalysisNameDto updateAnalysisNameDto) {
        RecoveryAnalysisDto recoveryAnalysisDto = analysisService.updateAnalysisName(id, updateAnalysisNameDto.name());
        
        return new ResponseEntity<>(recoveryAnalysisDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<RecoveryAnalysisDto>> getAnalysisByUser(@PathVariable Long id) {
        List<RecoveryAnalysisDto> analysies = analysisService.getAnalysisByUser(id);
        return new ResponseEntity<>(analysies, HttpStatus.ACCEPTED);
    }
    
    
    @GetMapping("/id/{id}")
    public ResponseEntity<RecoveryAnalysisDto> getAnalysis(@PathVariable Long id) {
        RecoveryAnalysisDto analysis = analysisService.getAnalysisById(id);
        return new ResponseEntity<>(analysis, HttpStatus.ACCEPTED);
    }

}
