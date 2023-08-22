package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtils {

    public static <T> T loadJsonFromFile(String resourceName, Class<T> responseClass){
        Path path = FilePath.getPath(resourceName);

        byte[] jsonData = new byte[0];

        try {
            jsonData = Files.readAllBytes(path);
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(jsonData, responseClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateJsonFileWithSchema(String url, String schemaFileName) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        Path path = FilePath.getPath(schemaFileName);
        com.fasterxml.jackson.databind.JsonNode jsonNodeSchema = null;
        try {
            jsonNodeSchema = mapper.readTree(new File(path.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String jsonResponse = null;
        com.fasterxml.jackson.databind.JsonNode jsonNode = null;
        try {
            jsonResponse = Unirest.get(url).asJson().getBody().toString();
            jsonNode = mapper.readTree(jsonResponse);
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ProcessingReport validator = null;
        try {
            validator = new JsonSchemaValidatorSettings().jsonSchemaFactory().getValidator().validate(jsonNodeSchema, jsonNode);
        } catch (ProcessingException e) {
            throw new RuntimeException(e);
        }

        return validator.isSuccess();
    }

    public static <T> T getJavaObjectFromJsonResponse(HttpResponse<JsonNode> jsonResponse, Class<T> responseClass) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try {
            return mapper.readValue(jsonResponse.getBody().toString(), responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> JSONObject getJsonObjectFromJavaObject(T dataObject) {
        JSONObject object = new JSONObject();
        Field[] fieldsList = dataObject.getClass().getDeclaredFields();
        for (Field field : fieldsList) {
            field.setAccessible(true);
            try {
                object.put(field.getName(), field.get(dataObject));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }
}
