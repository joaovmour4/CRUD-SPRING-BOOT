package com.example.api.detection;

import ai.onnxruntime.OrtException;

import com.example.api.dto.RecoveryDetectionDto;
import com.example.api.exceptions.ErrorCode;
import com.example.api.exceptions.UploadFileException;
import com.example.api.yolo.Detection;
import com.example.api.yolo.ImageUtil;
import com.example.api.yolo.ModelFactory;
import com.example.api.yolo.NotImplementedException;
import com.example.api.yolo.Yolo;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


@Service
public class DetectionService {
    static private final List<String> mimeTypes = Arrays.asList("image/png", "image/jpeg");
    private final Yolo inferenceSession;
    // private final Logger LOGGER = LoggerFactory.getLogger(DetectionService.class);

    private DetectionService() throws OrtException, IOException, NotImplementedException {
        ModelFactory modelFactory = new ModelFactory();
        this.inferenceSession = modelFactory.getModel("model.properties");
    }

    public RecoveryDetectionDto detection(MultipartFile uploadFile) throws OrtException, IOException {
        if (!mimeTypes.contains(uploadFile.getContentType())) throw new UploadFileException(ErrorCode.INVALID_MIME_TYPE);
        byte[] bytes = uploadFile.getBytes();
        Mat img = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_COLOR);
        List<Detection> result = inferenceSession.run(img);
        // List<Long> weeds = new ArrayList<Long>();
        // weeds.add(1L);
        // weeds.add(2L);
        // CreateAnalysisDto createAnalysisDto = new CreateAnalysisDto(1L, result.size() > 0, weeds, new Date(Calendar.getInstance().getTime().getTime()));
        // RecoveryAnalysisDto recoveryAnalysisDto = analysisService.createAnalysis(createAnalysisDto);        
        ImageUtil.drawPredictions(img, result);
        Date analysis_date = new Date(Calendar.getInstance().getTime().getTime());
        // Imgcodecs.imwrite("predictions.jpg", img);
        // LOGGER.info("POST 200");

        return new RecoveryDetectionDto(img, result, analysis_date);
    }
}
