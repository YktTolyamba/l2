package org.example.utils;

public class IdToStringHelper {

    public static String getPhotoId(int owner_id, int photoId) {
        return String.format("photo%d_%d", owner_id, photoId);
    }

    public static String getPostId(int owner_id, int postId) {
        return String.format("post%d_%d", owner_id, postId);
    }

}
