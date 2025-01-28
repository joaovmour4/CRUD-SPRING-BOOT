package com.example.api.mappers;

import java.util.List;
import java.util.Set;

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

    @Mapping(source = "images", target = "image_urls", qualifiedByName = "mapUrls")
    Set<Weed> weedsToWeedsWithImages(Set<Weed> weeds);

    @Mapping(source = "images", target = "image_urls", qualifiedByName = "mapUrls")
    Set<RecoveryWeedDto> weedsToWeedsDto(Set<Weed> weeds);

    @Named("mapUrls")
    default List<String> mapUrls(List<Weed_Image> images){
        return images.stream()
                     .map(Weed_Image::getUrl_s3)
                     .toList();
    }

}
