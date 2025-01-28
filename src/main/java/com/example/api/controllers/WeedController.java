package com.example.api.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.RecoveryWeedDto;
import com.example.api.services.WeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/weeds")
public class WeedController {

    @Autowired
    WeedService weedService;

    @GetMapping("/name/{name}")
    public ResponseEntity<RecoveryWeedDto> getWeedByName(@PathVariable String name) {
        RecoveryWeedDto weed = weedService.getWeedByPopularName(name);
        return new ResponseEntity<>(weed, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RecoveryWeedDto> getWeedById(@PathVariable long id) {
        RecoveryWeedDto weed = weedService.getWeedById(id);
        return new ResponseEntity<>(weed, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<Set<RecoveryWeedDto>> getWeedsWithImages(){
        Set<RecoveryWeedDto> weeds = weedService.getAllWeedsWithImageUrls();
        return new ResponseEntity<>(weeds, HttpStatus.ACCEPTED);
    }
    
}
