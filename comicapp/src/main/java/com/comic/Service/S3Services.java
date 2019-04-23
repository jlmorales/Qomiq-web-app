package com.comic.Service;

public interface S3Services {
    public void uploadFile(String bucketName, String keyName, String uploadFilePath);
    public void deleteFileFromS3Bucket(String fileName, String bucketName);
}