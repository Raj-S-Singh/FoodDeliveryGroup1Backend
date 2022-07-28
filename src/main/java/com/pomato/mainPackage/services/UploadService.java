package com.pomato.mainPackage.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
    public String uploadFile(MultipartFile file, String jwtToken) {
        try {
            User user = userRepository.findByJwtToken(jwtToken);
            if(user == null) {
                return "";
            }
            else if(user.getRole().equalsIgnoreCase("manager")) {
                File uploadedFile = convertMultiPartToFile(file);
                Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
                uploadedFile.delete();
                return  uploadResult.get("url").toString();
            }
            else{
                return "F";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
