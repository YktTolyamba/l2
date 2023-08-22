package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.util.Map;

import static org.example.constants.JsonKeys.*;
import static org.example.constants.QueryParameterNames.*;
import static org.example.constants.JsonFiles.*;

public class RequestHelper {
    private static final String apiUrl = CONFIG.getValue(API_URL_KEY).toString();
    private static final String token = TESTDATA.getValue(TOKEN_KEY).toString();
    private static final String apiVersion = TESTDATA.getValue(API_VERSION_KEY).toString();

    public static void setUpObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

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

    public static HttpResponse<JsonNode> sendPostRequest(String methodName, Map<String, Object> parameters) {
        setUpObjectMapper();
        try {
            return Unirest.post(apiUrl).header(CONTENT_TYPE_PARAMETER, CONTENT_TYPE_VALUE).routeParam(METHOD_NAME_PARAMETER, methodName).routeParam(PARAMETERS_PARAMETER, "").queryString(parameters).routeParam(ACCESS_TOKEN_PARAMETER, token).routeParam(API_VERSION_PARAMETER, apiVersion).asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<JsonNode> sendFilePostRequest(String photoUploadUrl, File photo) {
        try {
            return Unirest.post(photoUploadUrl).field(FILE_PARAMETER, photo).asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}
