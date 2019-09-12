package com.create.file.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
public class DocumentServiceImpl {

    public static Path docStorageLocation;

    @Autowired
    public DocumentServiceImpl(DocumentStoragePropaty documentStoragePropaty) throws IOException {
        this.docStorageLocation = Paths.get(documentStoragePropaty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
    }
}
