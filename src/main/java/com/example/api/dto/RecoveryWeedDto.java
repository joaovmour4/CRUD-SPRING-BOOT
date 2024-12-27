package com.example.api.dto;

import java.util.List;

public record RecoveryWeedDto(
    String scientificName,
    String popularName,
    String description,
    String quimicComponent,
    List<String> image_urls
) {
    
}
