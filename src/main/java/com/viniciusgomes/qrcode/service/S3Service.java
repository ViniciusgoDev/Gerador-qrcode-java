package com.viniciusgomes.qrcode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final Region region;

    public S3Service(@Value("${aws.s3.bucket-name}") String bucketName,
                     @Value("${aws.s3.region}") String regionName) {
        this.bucketName = bucketName;
        this.region = Region.of(regionName);

        this.s3Client = S3Client.builder()
                .region(this.region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String upload(byte[] dados, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("image/png")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(dados));

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }
}
