package com.ieee.girish.syw2015;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	
	@Override
    public void onCreate() {
        super.onCreate();
		Parse.initialize(this, "PnDUwd0EFtBYiCbZs5XnfIg9ZKKJn6fRD2XcPcvE", "OZ9Oiro9jH6m4J0Hke0Rp3zOVVBIsSCY46KnwZXj");
        // Also in this method, specify a default Activity to handle push notifications
        //PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
        
        ParsePush.subscribeInBackground("", new SaveCallback() {
        	  @Override
        	  public void done(ParseException e) {
        	    if (e != null) {
        	      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
        	    } else {
        	      Log.e("com.parse.push", "failed to subscribe for push", e);
        	    }
        	  }
        	});
    }

}
