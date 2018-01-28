package com.exercises.javid.contactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

public class ContactInfoActivity extends AppCompatActivity {

    private Activity context;
    private HashMap<String, String> record;
    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = ContactInfoActivity.this;
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            recordID = bundle.getString(DB_Handler.T_ID);
            if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                DB_Handler db_handler = new DB_Handler(context);
                record = db_handler.getContactRecord(recordID);
                if (record != null) {
                    loadContact();
                }
                db_handler.close();
            }
        }

        findViewById(R.id.btn_info_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                    deleteContact(recordID);
                }
            }
        });

        findViewById(R.id.btn_info_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                    editContact(recordID);
                }
            }
        });

    }

    private void loadContact() {
        ((TextView) findViewById(R.id.txt_info_name)).setText(record.get(DB_Handler.T_NAME));
        ((TextView) findViewById(R.id.txt_info_last_name)).setText(record.get(DB_Handler.T_L_NAME));
        ((TextView) findViewById(R.id.txt_info_phone)).setText(record.get(DB_Handler.T_PHONE));
        ((TextView) findViewById(R.id.txt_info_emaile)).setText(record.get(DB_Handler.T_EMAIL));
        ((TextView) findViewById(R.id.txt_info_address)).setText(record.get(DB_Handler.T_ADDRESS));
        ((TextView) findViewById(R.id.txt_info_desc)).setText(record.get(DB_Handler.T_DESC));
    }

    private void deleteContact(String id) {
        DB_Handler db_handler = new DB_Handler(context);
        db_handler.deleteContactRecord(id);
        db_handler.close();
        finish();
    }

    private void editContact(String id) {
        finish();
    }


}
