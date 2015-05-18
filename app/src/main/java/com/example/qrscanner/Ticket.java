package com.example.qrscanner;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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
    public static final String DATE = "date";

    public Ticket() {

    }

}
