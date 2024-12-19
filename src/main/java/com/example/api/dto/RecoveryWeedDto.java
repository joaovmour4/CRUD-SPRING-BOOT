package com.example.api.dto;

import java.util.List;

public record RecoveryWeedDto(
    Long id,
    String name,
    String description,
    String combat,
    List<String> image_urls
) {
    
}
