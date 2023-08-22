package org.example.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.example.constants.JsonResponseKeys.*;

public class JsonHelper {
    public static <T> Object getValueFromResponse(HttpResponse<JsonNode> response, String key) {
        return getJsonObjectFromResponse(response).get(key);
    }

    public static int getSavedPhotoIdFromResponse(HttpResponse<JsonNode> response) {
        return ((JSONArray) getObjectFromResponse(response, RESPONSE_KEY)).getJSONObject(0).getInt(PHOTO_ID_KEY);
    }

    public static int getLikedUserIdFromResponse(HttpResponse<JsonNode> response) {
        return getJsonObjectFromResponse(response).getJSONArray(USERS_KEY).getJSONObject(0).getInt(USER_ID_KEY);
    }

    public static <T> Object getObjectFromResponse(HttpResponse<JsonNode> response, String key) {
        return response.getBody().getObject().get(key);
    }

    private static JSONObject getJsonObjectFromResponse(HttpResponse<JsonNode> response) {
        return (JSONObject) getObjectFromResponse(response, RESPONSE_KEY);
    }
}
