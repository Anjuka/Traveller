package com.preeni.traveller_user.constant;

import java.util.HashMap;

public class Constants {

    public static final String SHARE_PREFERENCE_TAG = "TravelerPreference";
    public static final String FCM_TOKEN = "fcmToken";
    public static final String KEY_MESSAGE = "message";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAATiJiUSM:APA91bFnXq53czUmcSB8ZuEAT0RkvGR2I4P81PXEL7RN4PyNEOXEkJ52r-Ujiy8C5arKjMoaORS16NVekrcnY_-33B2e73mrG2wzJtqXS-Ug9I31rmWjfeSY0MXyWmQwXkM8APNTkCvy"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }
}
