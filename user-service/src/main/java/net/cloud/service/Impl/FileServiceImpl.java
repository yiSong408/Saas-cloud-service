package net.cloud.service.Impl;

import lombok.extern.slf4j.Slf4j;
import net.cloud.config.S3Config;
import net.cloud.service.FileService;
import net.cloud.util.CommonUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private S3Config s3Config;

    @Override
    public String uploadUserImg(MultipartFile file) {
        String bucketName = s3Config.getBucketName();
        String accessKey = s3Config.getAccessKey();
        String secretKey = s3Config.getSecretKey();
        String region = s3Config.getRegion();
        String regionUri = s3Config.getRegionUri();

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                accessKey, secretKey);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        String originalFilename = file.getOriginalFilename();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folder = now.format(dateTimeFormatter);
        String fileName = CommonUntil.generateUUid();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = "user/" + folder + "/" + fileName + extensionName;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(newFileName)
                    .build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if (putObjectResponse != null) {
                String imgUrl = "https://" + bucketName + ".s3." + regionUri + "/" + newFileName;
                return imgUrl;
            }
        } catch (IOException e) {
            log.error("Upload file error: {}", e.toString());
        } finally {
            s3Client.close();
        }

        return null;
    }
}
