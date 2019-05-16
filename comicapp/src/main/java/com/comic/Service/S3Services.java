package com.comic.Service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface S3Services {

    public S3Object downloadFile(String keyName);
    public void uploadFile(String keyName, S3Object s3Object);
    public void uploadFile(String keyName, MultipartFile uploadFilePath);
    public void deleteFileFromS3Bucket(String fileName);
}
