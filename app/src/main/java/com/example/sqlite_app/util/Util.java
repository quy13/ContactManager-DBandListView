package com.example.sqlite_app.util;

public class Util {

    //Db related items

    //this variable will never change, It also better to be ALL CAPS
    public static final int     DATABASE_VERSION = 1;
    public static final String  DATABASE_NAME = "contact_db";
    public static final String  TABLE_NAME = "contacts";
    //this is easier method to to change small item or variable that you wanted to change

    //Contacts table column names 159(11:37) for reference
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NUMBER = "phone_number";

}
