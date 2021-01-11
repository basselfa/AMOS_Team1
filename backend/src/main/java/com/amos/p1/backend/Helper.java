package com.amos.p1.backend;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Helper {

    public static String getFileResourceAsString(String resourcePath){
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);

        byte[] encoded = null;
        try {
            InputStream inputStream = classPathResource.getInputStream();
            encoded = ByteStreams.toByteArray(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if(encoded == null) throw new IllegalStateException("Getting file went wrong: " + resourcePath);

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

    public static String getIncidentListMarshalling(List<Incident> incidentList){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(incidentList);
            return getPrettyJsonList(json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
