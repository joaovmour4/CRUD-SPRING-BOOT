package com.example.api.dto;

import java.sql.Date;
import java.util.List;

import org.opencv.core.Mat;

import com.example.api.yolo.Detection;

public record RecoveryDetectionDto(
    Mat image,
    List<Detection> detections,
    Date analysis_date
) {
    
}
