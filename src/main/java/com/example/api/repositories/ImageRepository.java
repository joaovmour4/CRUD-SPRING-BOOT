package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
