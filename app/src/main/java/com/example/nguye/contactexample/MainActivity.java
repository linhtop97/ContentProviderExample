package com.example.nguye.contactexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nguye.contactexample.adapter.ContactAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IAppAction {

    private ContactAdapter contactAdapter;
    private RecyclerView rvContact;
    private List<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        rvContact = findViewById(R.id.rv_contact);
        mContacts = new Contact().getContact(this);
        contactAdapter = new ContactAdapter(this, mContacts, this);
        rvContact.setLayoutManager(new LinearLayoutManager(this));
        rvContact.setAdapter(contactAdapter);
    }


    @Override
    public void onItemClick(int positon) {
        Toast.makeText(this, mContacts.get(positon).getmName(), Toast.LENGTH_SHORT).show();
    }

}
