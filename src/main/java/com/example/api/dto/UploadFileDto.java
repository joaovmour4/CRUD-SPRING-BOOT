package com.example.api.dto;

import org.opencv.core.Mat;

import com.example.api.entities.Analysis;

public record UploadFileDto(
    Analysis analysis,
    Mat file
) {
    
}
