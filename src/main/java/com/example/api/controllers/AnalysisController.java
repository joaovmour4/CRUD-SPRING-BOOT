package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.CreateAnalysisDto;
import com.example.api.dto.RecoveryAnalysisDto;
import com.example.api.services.AnalysisService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    
    @Autowired
    private AnalysisService analysisService;

    @PostMapping()
    public ResponseEntity<RecoveryAnalysisDto> createAnalysis(@RequestBody @Valid CreateAnalysisDto createAnalysisDto) {
        RecoveryAnalysisDto analysis = analysisService.createAnalysis(createAnalysisDto);
        return new ResponseEntity<>(analysis, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryAnalysisDto> getAnalysis(@PathVariable Long id) {
        RecoveryAnalysisDto analysis = analysisService.getAnalysisById(id);
        return new ResponseEntity<>(analysis, HttpStatus.ACCEPTED);
    }
    

}
