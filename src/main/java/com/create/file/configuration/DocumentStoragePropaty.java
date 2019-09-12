package com.create.file.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "document")
@Component
@Getter
@Setter
public class DocumentStoragePropaty {

    private String uploadDirectory;
}
