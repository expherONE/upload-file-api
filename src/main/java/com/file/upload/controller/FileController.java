package com.file.upload.controller;

import com.file.upload.Service.StorageService;
import com.file.upload.dto.SystemResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/documents/{noPersonal}")
    SystemResponse singleUpload (@RequestParam("file") MultipartFile file, @PathVariable String noPersonal){
        String filename = storageService.guardar(file, noPersonal);

        //http://localhost:8080/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/pdf/")
                .path(filename)
                .toUriString();
        String contenType = file.getContentType();

        SystemResponse response = new SystemResponse(filename, contenType, url);
        return response;
    }
    @GetMapping("/download/{noPersonal}/{fileName}")
    ResponseEntity<Resource> viewPdf(@PathVariable String fileName, @PathVariable String noPersonal){
        Resource resource = StorageService.downloadFile(fileName, noPersonal);

        //la imagen se puede cambiar por PDF
        //aplicacion XML PDF
        //MediaType.IMAGE_JPEG; gif PNG
        MediaType contentType = MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .contentType(contentType)
                //este lo mantiene en linea para poder visualisarlo
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName"
                //este solo descarga directo el archivo
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachement;fileName"
                        +resource.getFilename())
                .body(resource);
    }
    @PostMapping("/dir/{noPersonal}")
    ResponseEntity makeDir (@PathVariable String noPersonal){
     storageService.makeDir(noPersonal);
     return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/files/{noPersonal}")
    SystemResponse saveDocuments (@RequestParam("file") MultipartFile file, @PathVariable String noPersonal){
        String filename = storageService.guardar(file, noPersonal);

        //http://localhost:8080/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/pdf/")
                .path(filename)
                .toUriString();
        String contenType = file.getContentType();

        SystemResponse response = new SystemResponse(filename, contenType, url);
        return response;
    }
}
