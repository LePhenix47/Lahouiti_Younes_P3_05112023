package com.openclassrooms.p3.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

/**
 * The `S3Service` class is a service class that provides a method for uploading
 * files to an Amazon S3 bucket.
 * It uses the AWS SDK for Java version 2.x to interact with the S3 service.
 */
@Service
public class S3Service {

    @Value("${aws.s3.accessKey}")
    private String accessKeyId;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * Uploads a file to the specified path in the S3 bucket.
     *
     * @param file The file to be uploaded.
     * @param path The path in the S3 bucket where the file should be uploaded.
     * @return The URL of the uploaded file.
     */
    public String uploadFile(MultipartFile file, String path) {
        try {
            // Create an instance of the S3 client
            S3Client s3Client = S3Client.builder()
                    .region(Region.EU_NORTH_1)
                    .credentialsProvider(
                            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
                    .build();

            String fileName = file.getOriginalFilename();

            // Determine the full key (path + filename)
            String fullKey = (path.isEmpty() ? "" : path + "/") + fileName;

            // Upload the file to the S3 bucket
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullKey)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Generate a pre-signed URL for the uploaded file
            URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fullKey));

            // Return the URL of the uploaded file
            return url.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }
}
