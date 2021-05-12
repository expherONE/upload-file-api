package com.file.upload.controller;

import com.file.upload.Service.StorageService;
import com.file.upload.dto.SystemResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {
    private StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/single/upload")
    SystemResponse singleUpload (@RequestParam("file") MultipartFile file){
        String filename = storageService.guardar(file);

        //http://localhost:8080/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(filename)
                .toUriString();
        String contenType = file.getContentType();

        SystemResponse response = new SystemResponse(filename, contenType, url);
        return response;
    }
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName){
        Resource resource = StorageService.downloadFile(fileName);

        MediaType contentType = MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement;fileName"
                        +resource.getFilename())
                .body(resource);
    }
}
