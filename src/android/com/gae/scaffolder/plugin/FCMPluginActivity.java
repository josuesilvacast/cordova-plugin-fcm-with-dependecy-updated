package com.gae.scaffolder.plugin;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class FCMPluginActivity extends Activity {
    private static String TAG = "FCMPlugin";

    /*
     * this activity will be started if the user touches a notification that we own. 
     * We send it's data off to the push plugin for processing.
     * If needed, we boot up the main activity to kickstart the application. 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		Map<String, Object> data = new HashMap<String, Object>();
        if (getIntent().getExtras() != null) {
			data.put("wasTapped", true);
			for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
				data.put(key, value);
            }
        }
		
		FCMPlugin.sendPushPayload(data);

        finish();

        forceMainActivityReload();
    }

    private void forceMainActivityReload() {
        PackageManager pm = getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        startActivity(launchIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

}