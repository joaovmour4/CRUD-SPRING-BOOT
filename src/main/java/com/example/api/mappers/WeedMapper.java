package com.example.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.api.dto.RecoveryWeedDto;
import com.example.api.entities.Weed;
import com.example.api.entities.Weed_Image;

@Mapper(componentModel = "spring")
public interface WeedMapper {
    
    @Mapping(source = "images", target = "image_urls", qualifiedByName = "mapUrls")
    RecoveryWeedDto weedToWeedDto(Weed weed);

    @Named("mapUrls")
    default List<String> mapUrls(List<Weed_Image> images){
        return images.stream()
                     .map(Weed_Image::getUrl_s3)
                     .toList();
    }

}