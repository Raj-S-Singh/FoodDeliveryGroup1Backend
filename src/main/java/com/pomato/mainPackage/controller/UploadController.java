package com.pomato.mainPackage.controller;

import com.cloudinary.*;

import com.pomato.mainPackage.model.UploadResponse;
import com.pomato.mainPackage.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadimage")
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("file") MultipartFile file, @RequestHeader(name="jwtToken") String jwtToken) {
        String url = uploadService.uploadFile(file, jwtToken);
        UploadResponse response = new UploadResponse();
        if(url.equals("")){
            response.setMessage("User not found");
            response.setStatus(false);
            response.setUrl("");
            return new ResponseEntity<UploadResponse>(response,HttpStatus.BAD_REQUEST);
        }
        else if(url.equals("F")) {
            response.setMessage("User is not a manager");
            response.setStatus(false);
            response.setUrl("");
            return new ResponseEntity<UploadResponse>(response,HttpStatus.BAD_REQUEST);
        }
         else {
            response.setMessage("Success");
            response.setStatus(true);
            response.setUrl(url);
             return new ResponseEntity<UploadResponse>(response, HttpStatus.OK);
        }
    }
}
