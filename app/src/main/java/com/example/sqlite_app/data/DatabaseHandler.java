package com.example.sqlite_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sqlite_app.R;
import com.example.sqlite_app.model.Contact;
import com.example.sqlite_app.util.Util;
import java.util.ArrayList;
import java.util.List;

//this class will handle all the database action

public class DatabaseHandler extends SQLiteOpenHelper {

    //this only Create a new data base which need a context ,db name , CursorFactory don't really need it
    public DatabaseHandler(Context context) {
        super(context, Util.KEY_NAME, null, Util.DATABASE_VERSION);
    }

    //this id where we create our table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID+" INTEGER PRIMARY KEY,"+Util.KEY_NAME +" TEXT,"
                + Util.KEY_PHONE_NUMBER+" TEXT"+")";
        //THIS IS WHAT IT LOOK LIKE WITHOUT CONCATENATING
        /*
         create table _name(id A.I, name, phone number)
         note: A.I means auto increment or id++
        */
        db.execSQL(CREATE_CONTACT_TABLE); //creating a table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //should use for upgrade/change current db with a new one
        String DROP_TABLE = String.valueOf(R.string.db_drop_table);
        /*
        U can also put anything else inside too
             Like a String "random_string"
        */
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        //                                                 ^.Right here :)
        //Create a table again
        onCreate(db);

    }
    /*
     CRUD = Create, Read, Update, Delete
     */

    //add Contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        //use this to put data inside db
        ContentValues values= new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //Insert to row
        db.insert(Util.TABLE_NAME,null, values);
        Log.d("DBHandler", "addContact: " + "Items added ");
        db.close();//closing the connection to db

    }

    //Get a contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        //cursor is just an Object that can Iterate(looping)
        // through a database (inside of the table row)
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?",new String[]{String.valueOf(id)},
                null,null,null);

        //making sure cursor isn't null then point it to first
        if (cursor != null){
            cursor.moveToFirst();
        }

        //making a new contact Object
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));


        return contact;
    }

    //Get all Contacts
    public List<Contact> getAllContacts(){

        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        //cursor always need getReadableDatabase
        Cursor cursor = db.rawQuery(selectAll,null);


        //loop through all the data
        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact objects to our list
                contactList.add(contact);
            }while (cursor.moveToNext()); // this will keep going until the cursor stop
        }

        return contactList;
    }

    //Update contact
    //when update the system will return id of the row or a int
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update the row
        //update (table name, values, where id = 43)
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{
                        String.valueOf(contact.getId())
                });

    }
    //delete single Contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{
                        String.valueOf(contact.getId())
                });

        db.close();
    }

    //get contact count
    public int getCount(){
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(countQuery,null);

        return cursor.getCount();
    }

}
