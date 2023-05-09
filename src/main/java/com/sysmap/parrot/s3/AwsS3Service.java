package com.sysmap.parrot.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.exception.user.ShowImageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private static final String FILE_EXTENSION = "fileExtension";

    @Value("${config.aws.s3.bucket-name}")
    private String bucketName;

    private  final S3ClientConfiguration s3ClientConfiguration;

    public String uploadUserPictureProfile(MultipartFile multipartFile, String id) {
        String key = RandomStringUtils.randomAlphanumeric(10);

        String fileUrl = "http://localhost:8080/api/v1/users/" +id + "/profile/picture/" + key;
        try {
            s3ClientConfiguration.amazonS3()
                    .putObject(bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
        } catch (IOException e){
            throw new ShowImageException();
        }

        return fileUrl;
    }

    public String uploadPostPicture(MultipartFile multipartFile) {
        String key = RandomStringUtils.randomAlphanumeric(16);

        String fileUrl = "http://localhost:8080/api/v1/posts/image/" + key;
        try {
            s3ClientConfiguration.amazonS3()
                    .putObject(bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
        } catch (IOException e){
            throw new ShowImageException();
        }

        return fileUrl;
    }

    public S3Object downloadImage(String pictureKey) {
        return s3ClientConfiguration.amazonS3().getObject(bucketName, pictureKey);
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        objectMetadata.getUserMetadata().put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));

        return objectMetadata;
    }
}
