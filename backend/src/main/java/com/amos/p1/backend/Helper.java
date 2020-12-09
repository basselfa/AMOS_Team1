package com.amos.p1.backend;

import org.json.JSONException;
import org.json.JSONObject;
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

        return new String(encoded, StandardCharsets.UTF_8);

    }

    public static String getPrettyJson(String rawJson){
        try {
            JSONObject jsonObject = new JSONObject(rawJson);
            return jsonObject.toString(3);
        } catch (JSONException e) {
            throw new IllegalStateException("Can't prettify this json: " + rawJson);
        }
    }

    public static String getPrettyJsonList(String rawJson){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{list:")
                .append(rawJson)
                .append("}");

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return jsonObject.toString(3);
        } catch (JSONException e) {
            throw new IllegalStateException("Can't prettify this json: " + rawJson);
        }
    }
}
