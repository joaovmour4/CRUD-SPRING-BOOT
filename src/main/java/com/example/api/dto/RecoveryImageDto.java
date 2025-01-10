package com.example.api.dto;

import java.sql.Date;

public record RecoveryImageDto(
    String url_s3,
    String type,
    Date upload_date
) {
    
}
