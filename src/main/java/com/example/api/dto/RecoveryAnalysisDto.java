package com.example.api.dto;

import java.sql.Date;
import java.util.List;

import com.example.api.entities.Weed;

public record RecoveryAnalysisDto(
    Long idAnalysis,
    List<Weed> weeds,
    Boolean result,
    Date analysis_date
) {
    
}
