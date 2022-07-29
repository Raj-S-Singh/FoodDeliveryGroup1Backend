package com.pomato.mainPackage.controller;

import com.cloudinary.*;

import com.pomato.mainPackage.model.UploadResponse;
import com.pomato.mainPackage.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        UploadResponse response = uploadService.uploadFile(file);
        if(response.isStatus()){
            return new ResponseEntity<UploadResponse>(response,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<UploadResponse>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
