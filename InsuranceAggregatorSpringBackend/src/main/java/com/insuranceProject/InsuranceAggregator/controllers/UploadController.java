/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.insuranceProject.InsuranceAggregator.controllers;

import com.insuranceProject.InsuranceAggregator.inputTypes.UploadTypeDto;
import com.insuranceProject.InsuranceAggregator.models.User;
import com.insuranceProject.InsuranceAggregator.repositories.UserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author pcran
 */
@RestController
@RequestMapping("/api/v1")
public class UploadController {

    @Autowired
    private UserRepository userRepo;
    @Value("${upload.location}")
    private String uploadPath;
    @Value("${document.location}")
    private String documentPath;

    //create two apis here, one for kyc and one for uploading profile image
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/verifyKyc/{id}")
    public String verifyKyc(@RequestParam("file") MultipartFile file, @PathVariable("id") ObjectId id) throws IOException, TesseractException {
        User user = userRepo.findById(id).orElseThrow();
        Path filePath = Paths.get(documentPath, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        File image = new File(documentPath + "/" + file.getOriginalFilename());
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        //this string should be from the user's input for aadhar card
        String docId = user.docNumber;
        //check the above string is present in the aadhar or not for the user input, if not then tell the user to reupload the image
        if (result.contains(docId)) {
            user.kyc = true;
            user.document.add(documentPath + "/" + file.getOriginalFilename());
            userRepo.save(user);
            return documentPath + "/" + file.getOriginalFilename();
        } else {
            return "Please Try Again With a Better Image";
        }
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/addDocument/{id}")
    public String addDocument(@RequestParam("file") MultipartFile file, @PathVariable("id") ObjectId id) throws IOException {
        User user = userRepo.findById(id).orElseThrow();
        Path filePath = Paths.get(documentPath, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        user.document.add(documentPath + "/" + file.getOriginalFilename());
        userRepo.save(user);
        return "uploaded";
    }

    @PostMapping("/uploadProfile")
    public String uploadProfile(@RequestParam("file") MultipartFile file) throws IOException {
        Path filePath = Paths.get(uploadPath, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return uploadPath + "/" + file.getOriginalFilename();
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(@RequestBody UploadTypeDto upload) throws IOException {
        Path filePath = Paths.get(upload.getPath());
        Files.deleteIfExists(filePath);
        return "deleted";
    }
}
