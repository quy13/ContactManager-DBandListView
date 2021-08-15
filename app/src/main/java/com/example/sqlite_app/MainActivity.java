package com.example.sqlite_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sqlite_app.model.Contact;
import com.example.sqlite_app.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        contactArrayList = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        //count the amount of item in database
        Log.d("Count", "onCreate: "+ db.getCount());


        //adding contact to list view
//        db.addContact(new Contact("James","0293837"));
//        db.addContact(new Contact("Greg","9053467"));
//        db.addContact(new Contact("Helena","2454677"));
//        db.addContact(new Contact("Carimo","3635443"));
//
//        db.addContact(new Contact("Santos","8345321"));
//        db.addContact(new Contact("Litos","4225357"));
//        db.addContact(new Contact("Karate","7359777"));
//        db.addContact(new Contact("Guerra","3547995"));
//        db.addContact(new Contact("Gema","2843866"));

        List<Contact> contactList = db.getAllContacts();
        for(Contact contact : contactList){
            Log.d("TAG", "onCreate: " + contact.getName());
            contactArrayList.add(contact.getName());

            //create Array adapter
            contactAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,//using a built-in(premade) layout inside android studio
                    contactArrayList//passing data values
            );

            //add to listview
            listView.setAdapter(contactAdapter);
            //attaching event listener to listview
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Log.d("List", "onItemClick: " + position + " " + contactArrayList.get(position));
            });
        }
    }
}