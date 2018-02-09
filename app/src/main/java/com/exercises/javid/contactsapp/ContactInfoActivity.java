package com.exercises.javid.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

public class ContactInfoActivity extends AppCompatActivity {

    private Activity context;
    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = ContactInfoActivity.this;
        Button delete = (Button) findViewById(R.id.btn_info_delete);
        Button edit = (Button) findViewById(R.id.btn_info_edit);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            recordID = bundle.getString(Constant.T_ID);
            if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                loadContact(recordID);
            }
        }


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                    deleteContact(recordID);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                    editContact(recordID);
                }
            }
        });

    }

    private void loadContact(String id) {

        DatabaseHandler db_handler = new DatabaseHandler(
                context,
                Constant.DB_NAME,
                null,
                Constant.VERSION
        );

        HashMap<String, String> record = db_handler.getContactRecord(id);

        if (record != null) {
            ((TextView) findViewById(R.id.txt_info_name)).setText(record.get(Constant.T_NAME));
            ((TextView) findViewById(R.id.txt_info_last_name)).setText(record.get(Constant.T_L_NAME));
            ((TextView) findViewById(R.id.txt_info_phone)).setText(record.get(Constant.T_PHONE));
            ((TextView) findViewById(R.id.txt_info_email)).setText(record.get(Constant.T_EMAIL));
            ((TextView) findViewById(R.id.txt_info_address)).setText(record.get(Constant.T_ADDRESS));
            ((TextView) findViewById(R.id.txt_info_desc)).setText(record.get(Constant.T_DESC));
        }

        db_handler.close();
    }

    private void deleteContact(String id) {

        DatabaseHandler db_handler = new DatabaseHandler(
                context,
                Constant.DB_NAME,
                null,
                Constant.VERSION
        );

        db_handler.deleteContactRecord(id);
        db_handler.close();
        finish();
    }

    private void editContact(String id) {
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra(Constant.T_ID, id);
        startActivity(intent);
    }
}
