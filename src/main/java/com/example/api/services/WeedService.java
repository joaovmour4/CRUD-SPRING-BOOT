package com.example.api.services;

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

    public RecoveryWeedDto getWeedByName(String name){
        Weed weed = weedRepository.findByName(name)
                                    .orElseThrow(()-> new ResourceNotFoundException("Não Encontrado"));

        return weedMapper.weedToWeedDto(weed);
    }
    
}
