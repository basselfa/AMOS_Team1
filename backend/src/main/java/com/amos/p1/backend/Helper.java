package com.amos.p1.backend;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helper {

    public static String getFileResourceAsString(String resourcePath){
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);

        byte[] encoded = null;
        try {
            Path absolutePath = classPathResource.getFile().toPath();
            encoded = Files.readAllBytes(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(encoded == null) throw new IllegalStateException("Getting file went wrong");

        return new String(encoded, StandardCharsets.US_ASCII);

    }
}
