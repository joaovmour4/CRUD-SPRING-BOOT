package com.example.api.dto;

import java.sql.Date;
import java.util.Set;

public record CreateAnalysisDto(
    Long idUser,
    Boolean result,
    Set<Long> weeds,
    Date analysis_date
) {
    
}
