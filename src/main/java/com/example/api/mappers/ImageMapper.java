package com.example.api.mappers;

import org.mapstruct.Mapper;

import com.example.api.dto.RecoveryImageDto;
import com.example.api.entities.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    RecoveryImageDto recoveryImageToDto(Image image);

}
