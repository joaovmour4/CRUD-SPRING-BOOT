package com.example.api.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.detection.DetectionService;
import com.example.api.dto.RecoveryImageDto;
import com.example.api.services.ImageService;
import com.example.api.yolo.Detection;

import ai.onnxruntime.OrtException;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private ImageService imageService;

    @Autowired
    private DetectionService detectionService;

    @PostMapping
    public ResponseEntity<RecoveryImageDto> uploadImage(@RequestParam("file") MultipartFile file) throws OrtException, IOException {
        
        RecoveryImageDto image = imageService.uploadImage(file);
        List<Detection> result = detectionService.detection(file);
        
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
    


}
