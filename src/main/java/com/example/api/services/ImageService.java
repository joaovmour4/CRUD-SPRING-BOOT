package com.example.api.services;

import java.io.IOException;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.Calendar;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.dto.RecoveryImageDto;
import com.example.api.dto.UploadFileDto;
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

    @Autowired
    private S3UploaderService s3UploadersService;

    public Image uploadImage(UploadFileDto uploadFileDto) throws IOException{

        String imageUrl = s3UploadersService.uploadAnalysisImageToBucketS3(uploadFileDto.file(), uploadFileDto.analysis().getId());

        Image image = Image.builder()
            .analysis(uploadFileDto.analysis())
            .url_s3(imageUrl)
            .type("image/jpeg")
            .upload_date(new Date(Calendar.getInstance().getTime().getTime()))
            .build();

        Image imageSaved = imageRepository.save(image);

        return imageSaved;
    }
    public String uploadProfileImage(MultipartFile file, Long idUsuario) throws IOException{

        String imageUrl = s3UploadersService.uploadProfileImageToBucketS3(file, idUsuario);

        return imageUrl;
    }

    public String uploadThumbnail(UploadFileDto uploadFileDto) throws IOException{
        String thumbnailUrl = s3UploadersService.uploadAnalysisThumbnailToBucketS3(uploadFileDto.file(), uploadFileDto.analysis().getId());
        return thumbnailUrl;
    }

    public void saveImage(Mat image, Long id){
        Imgcodecs.imwrite(MessageFormat.format("analysis_{0}.jpeg", id), image);
    }

    public RecoveryImageDto getImageById(Long id){
        Image image = imageRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Não há imagem cadastrada com o id informado."));

        return imageMapper.recoveryImageToDto(image);
    }

    
}
