package com.example.api.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.RecoveryWeedDto;
import com.example.api.entities.Weed;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.WeedMapper;
import com.example.api.repositories.WeedRepository;

@Service
public class WeedService {

    @Autowired
    WeedRepository weedRepository;

    @Autowired
    WeedMapper weedMapper;
    
    public RecoveryWeedDto getWeedById(long id){
        Weed weed = weedRepository.findByIdWithImages(id)
                                    .orElseThrow(()-> new ResourceNotFoundException("Não encontrado"));
        
        return weedMapper.weedToWeedDto(weed);
    }

    public RecoveryWeedDto getWeedByPopularName(String name){
        Weed weed = weedRepository.findByPopularName(name)
                                    .orElseThrow(()-> new ResourceNotFoundException("Não Encontrado"));

        return weedMapper.weedToWeedDto(weed);
    }

    public Set<RecoveryWeedDto> getAllWeedsWithImageUrls(){
        Set<Weed> weedsList = weedRepository.findAllWithImages();
        return weedMapper.weedsToWeedsDto(weedsList);
    }

    public Set<Weed> getWeedsByArrayId(List<Long> weeds){
        Set<Weed> weedsList = weedRepository.findAllByIds(weeds);
        return weedMapper.weedsToWeedsWithImages(weedsList);
    }

    public Set<Weed> getWeedsByArrayNames(List<String> weeds){
        Set<Weed> weedsList = weedRepository.findAllByNames(weeds);
        return weedMapper.weedsToWeedsWithImages(weedsList);
    }
    
}
