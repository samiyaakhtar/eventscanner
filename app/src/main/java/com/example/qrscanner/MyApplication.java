package com.example.qrscanner;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by samiya on 12/05/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupParse();
    }

    protected void setupParse() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, Config.PARSE_APP_ID, Config.PARSE_CLIENT_ID);

        ParseObject.registerSubclass(Ticket.class);
    }
}
