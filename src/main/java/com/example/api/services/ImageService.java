package com.example.api.services;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.dto.RecoveryImageDto;
import com.example.api.entities.Image;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.ImageMapper;
import com.example.api.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    public RecoveryImageDto uploadImage(MultipartFile file){
        if(file.isEmpty()){
            throw new ResourceNotFoundException("Nenhum arquivo encontrado na requisição.");
        }

        String imageUrl = MessageFormat.format("https://example.com/images/{0}", file.getOriginalFilename());

        Image image = Image.builder()
            .url_s3(imageUrl)
            .type(file.getContentType())
            .upload_date(new Date(Calendar.getInstance().getTime().getTime()))
            .build();

        Image imageSaved = imageRepository.save(image);

        return imageMapper.recoveryImageToDto(imageSaved);
    }

    public RecoveryImageDto getImageById(Long id){
        Image image = imageRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Não há imagem cadastrada com o id informado."));

        return imageMapper.recoveryImageToDto(image);
    }

    
}
