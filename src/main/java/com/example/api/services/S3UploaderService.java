package com.example.api.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3UploaderService {
    
    public String uploadAnalysisImageToBucketS3(Mat file, Long idAnalysis) throws IOException{
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build();
        
        String bucketName = "app-ervas-images";
        String objectKey = "analysis/analysis_"+idAnalysis+".jpg";

        InputStream inputStream = convertMatToInputStream(file);
        int fileSize = inputStream.available();

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

            s3.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));

        } catch (Exception e) {
            System.err.println("Erro ao enviar o arquivo" + e.getMessage());
        } finally {
            s3.close();
        }

        return "https://app-ervas-images.s3.sa-east-1.amazonaws.com/" + objectKey;
    }
    public String uploadAnalysisThumbnailToBucketS3(Mat file, Long idAnalysis) throws IOException{
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build();
        
        String bucketName = "app-ervas-images";
        String objectKey = "analysis/thumbnails/analysis_"+idAnalysis+".jpg";

        Mat thumbnail = resizeMat(file, 150, 150);

        InputStream inputStream = convertMatToInputStream(thumbnail);
        int fileSize = inputStream.available();

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

            s3.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));

        } catch (Exception e) {
            System.err.println("Erro ao enviar o arquivo" + e.getMessage());
        } finally {
            s3.close();
        }

        return "https://app-ervas-images.s3.sa-east-1.amazonaws.com/" + objectKey;
    }

    public String uploadProfileImageToBucketS3(MultipartFile file, Long idUsuario) throws IOException{
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build();
        
        String bucketName = "app-ervas-images";
        String objectKey = "users/profile/user_"+idUsuario+".jpg";

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

            s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (Exception e) {
            System.err.println("Erro ao enviar o arquivo" + e.getMessage());
        } finally {
            s3.close();
        }

        return "https://app-ervas-images.s3.sa-east-1.amazonaws.com/" + objectKey;
    }

    public InputStream convertMatToInputStream(Mat image) {
        // Codifica a imagem em formato JPEG
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte);

        // Converte para InputStream
        byte[] byteArray = matOfByte.toArray();
        return new ByteArrayInputStream(byteArray);
    }

    private Mat resizeMat(Mat image, int width, int height){
        Mat resizedImage = new Mat();

        Imgproc.resize(image, resizedImage, new Size(width, height), 0, 0, Imgproc.INTER_AREA);
        
        return resizedImage;
    }

}
