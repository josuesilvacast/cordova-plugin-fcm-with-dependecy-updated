package com.gae.scaffolder.plugin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Map;
import java.util.HashMap;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMPlugin";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        FCMPlugin.sendTokenRefresh(token);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("wasTapped", false);

        if (remoteMessage.getNotification() != null) {
            data.put("title", remoteMessage.getNotification().getTitle());
            data.put("body", remoteMessage.getNotification().getBody());
        }

        for (String key : remoteMessage.getData().keySet()) {
            Object value = remoteMessage.getData().get(key);
            data.put(key, value);
        }

        FCMPlugin.sendPushPayload(data);
    }
    // [END receive_message]
}
