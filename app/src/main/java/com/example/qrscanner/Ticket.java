package com.example.qrscanner;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by samiya on 18/05/15.
 */
@ParseClassName(Ticket.CLASSNAME)
public class Ticket extends ParseObject {
    public static final String CLASSNAME = "Ticket";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";
    public static final String QRCODE = "qrcode";
    public static final String ENTERED = "entered";

    public Ticket() {

    }

    public String getName() {
        return getString(NAME);
    }

    public boolean getEntryStatus() {
        return getBoolean(ENTERED);
    }

    public static void getTicketByQRContent(String qrContent, GetCallback<Ticket> callback) {
        ParseQuery<Ticket> query = ParseQuery.getQuery(Ticket.CLASSNAME);
        query.whereEqualTo(Ticket.QRCODE, qrContent);

        query.getFirstInBackground(callback);
    }

    public void updateEntryStatus(boolean entered) {
        put(ENTERED, entered);
        saveInBackground();
    }

}
