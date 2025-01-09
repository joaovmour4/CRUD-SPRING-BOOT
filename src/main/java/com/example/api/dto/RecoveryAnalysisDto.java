package com.example.api.dto;

import java.sql.Date;
import java.util.Set;

public record RecoveryAnalysisDto(
    Long id,
    Set<RecoveryWeedDto> weeds,
    Boolean result,
    Date analysis_date
) {
    
}
