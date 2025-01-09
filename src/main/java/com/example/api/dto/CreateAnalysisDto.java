package com.example.api.dto;

import java.sql.Date;
import java.util.List;

public record CreateAnalysisDto(
    Long idUser,
    Boolean result,
    List<Long> weeds,
    Date analysis_date
) {
    
}
