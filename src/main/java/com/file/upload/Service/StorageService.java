package com.file.upload.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private static String fileStorageLocation;
    Path fileStoragePath;

    public StorageService(@Value("${file.storage.location:temp}") String fileStorageLocation){
        this.fileStorageLocation=fileStorageLocation;
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        }catch (IOException e){
            throw new RuntimeException("No jalo este pedo"); } }



    public String guardar(MultipartFile file, String noPersonal){
        this.makeDir(noPersonal);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(fileStoragePath+"/"+noPersonal+"/"+fileName);
        try {
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public String makeDir(String noPersonal){
        File directorios = new File("/home/public/"+noPersonal);
        if (directorios.exists())
            System.out.println("El directorio ya existe");
        else{
            directorios.mkdir();
            System.out.println("Se creó la carpeta");}
        return noPersonal;
    }
    

    public static Resource downloadFile(String fileName, String noPersonal){
        //ruta absoluta
       //Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Path path = Paths.get(fileStorageLocation+"/"+noPersonal+"/").toAbsolutePath().resolve(fileName);
       Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (resource.exists() && resource.isReadable()){
            return resource;
        }else {
            throw new RuntimeException("No se encontro el archivo");
        }
    }

    public String saveDocuments(MultipartFile file, String noPersonal){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(fileStoragePath+"/"+noPersonal+"/"+fileName);
        try {
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }
}
