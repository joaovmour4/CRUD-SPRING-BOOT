package com.example.api.dto;

import java.sql.Date;

public record CreateAnalysisDto(
    Long idUser,
    Boolean result,
    Date analysis_date
) {
    
}
