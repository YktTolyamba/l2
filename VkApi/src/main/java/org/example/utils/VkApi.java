package org.example.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.example.constants.JsonKeys.*;
import static org.example.constants.ApiMethodNames.*;
import static org.example.constants.QueryParameterNames.*;
import static org.example.constants.JsonFiles.*;

public class VkApi {
    private static final String ownerId = TESTDATA.getValue(OWNER_ID_KEY).toString();
    private static final String albumId = TESTDATA.getValue(ALBUM_ID_KEY).toString();


    public static HttpResponse<JsonNode> createPost(String message) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(OWNER_ID_PARAMETER, ownerId);
        parameters.put(MESSAGE_PARAMETER, message);
        return RequestHelper.sendPostRequest(CREATE_POST_METHOD, parameters);
    }

    public static HttpResponse<JsonNode> getPhotoUploadServer() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ALBUM_ID_PARAMETER, albumId);
        return RequestHelper.sendPostRequest(GET_UPLOAD_SERVER_METHOD, parameters);
    }

    public static HttpResponse<JsonNode> uploadPhoto(String photoUploadUrl, File photo) {
        return RequestHelper.sendFilePostRequest(photoUploadUrl, photo);
    }

    public static HttpResponse<JsonNode> saveUploadedPhoto(HttpResponse<JsonNode> jsonResponse) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ALBUM_ID_PARAMETER, albumId);
        parameters.put(PHOTOS_LIST_PARAMETER, ((String) JsonHelper.getObjectFromResponse(jsonResponse, PHOTOS_LIST_PARAMETER)).replace("\\\"", "\""));
        parameters.put(SERVER_PARAMETER, JsonHelper.getObjectFromResponse(jsonResponse, SERVER_PARAMETER));
        parameters.put(HASH_PARAMETER, JsonHelper.getObjectFromResponse(jsonResponse, HASH_PARAMETER));
        return RequestHelper.sendPostRequest(SAVE_PHOTO_METHOD, parameters);
    }

    public static HttpResponse<JsonNode> performActionWithPost(String methodName, Map<String, Object> parameters, int postId) {
        parameters.put(OWNER_ID_PARAMETER, ownerId);
        parameters.put(POST_ID_PARAMETER, postId);
        return RequestHelper.sendPostRequest(methodName, parameters);
    }

    public static void editPost(int postId, String attachmentId, String message) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(MESSAGE_PARAMETER, message);
        parameters.put(ATTACHMENT_PARAMETER, attachmentId);
        performActionWithPost(EDIT_POST_METHOD, parameters, postId);
    }

    public static void commentPost(int postId, String message) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(MESSAGE_PARAMETER, message);
        performActionWithPost(CREATE_COMMENT_METHOD, parameters, postId);
    }

    public static HttpResponse<JsonNode> getPostLikes(int postId) {
        Map<String, Object> parameters = new HashMap<>();
        return performActionWithPost(GET_POST_LIKES_METHOD, parameters, postId);
    }

    public static void deletePost(int postId) {
        Map<String, Object> parameters = new HashMap<>();
        performActionWithPost(DELETE_POST_METHOD, parameters, postId);
    }
}
