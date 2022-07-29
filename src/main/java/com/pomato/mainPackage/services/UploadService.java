package com.pomato.mainPackage.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pomato.mainPackage.model.UploadResponse;
import com.pomato.mainPackage.model.User;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class UploadService {
    @Autowired
    private Cloudinary cloudinaryConfig;

    @Autowired
    private UserRepository userRepository;


    public UploadResponse uploadFile(MultipartFile file) {
        UploadResponse response = new UploadResponse();
        try {
                File uploadedFile = convertMultiPartToFile(file);
                Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
                uploadedFile.delete();
                response.setMessage("Success");
                response.setStatus(true);
                response.setUrl(uploadResult.get("url").toString());
                return response;
        } catch (Exception e) {
            response.setMessage("Error");
            response.setStatus(false);
            response.setUrl("");
            return response;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
