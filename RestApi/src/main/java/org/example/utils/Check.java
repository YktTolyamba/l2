package org.example.utils;

import org.example.dataclasses.PostData;

public class Check {

    public static boolean areIdsAscending(PostData[] firstModelList) {
        int prevId = -1;
        for (PostData elem : firstModelList) {
            if ( elem.getId() <= prevId ) {
                return false;
            }
        }
        return true;
    }

}
