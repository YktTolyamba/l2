package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.inject.internal.MoreTypes;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.type.TypeDescription;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class Api {

    public static void setUpObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static HttpResponse<JsonNode> sendGetRequest(String url) {
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson();

            return jsonResponse;
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<JsonNode> sendPostRequest(JSONObject object, String url) {
        setUpObjectMapper();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .header("Content-type", "application/json")
                    .body(object)
                    .asJson();

            return jsonResponse;
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
