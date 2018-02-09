package com.exercises.javid.contactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

public class EditContactActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private EditText address;
    private EditText desc;

    private Button save;
    private Button cancel;

    private Activity context;
    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = EditContactActivity.this;

        name = findViewById(R.id.edit_name);
        lastName = findViewById(R.id.edit_last_name);
        phone = findViewById(R.id.edit_phone);
        email = findViewById(R.id.edit_email);
        address = findViewById(R.id.edit_address);
        desc = findViewById(R.id.edit_desc);

        save = (Button) findViewById(R.id.btn_edit_save);
        cancel = (Button) findViewById(R.id.btn_edit_cancel);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            recordID = bundle.getString(Constant.T_ID);
            if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                loadContact(recordID);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Objects.equals(recordID, null) && Objects.equals(recordID, ""))) {
                    editContact(recordID);
                    Toast.makeText(context, "Contact edited successfully", Toast.LENGTH_SHORT).show();
                    cancel.setText("OK");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            ((TextView) findViewById(R.id.edit_name)).setText(record.get(Constant.T_NAME));
            ((TextView) findViewById(R.id.edit_last_name)).setText(record.get(Constant.T_L_NAME));
            ((TextView) findViewById(R.id.edit_phone)).setText(record.get(Constant.T_PHONE));
            ((TextView) findViewById(R.id.edit_email)).setText(record.get(Constant.T_EMAIL));
            ((TextView) findViewById(R.id.edit_address)).setText(record.get(Constant.T_ADDRESS));
            ((TextView) findViewById(R.id.edit_desc)).setText(record.get(Constant.T_DESC));
        }

        db_handler.close();
    }

    private void editContact(String id) {
        String name = this.name.getText().toString();
        String lastName = this.lastName.getText().toString();
        String phone = this.phone.getText().toString();
        String email = this.email.getText().toString();
        String address = this.address.getText().toString();
        String desc = this.desc.getText().toString();

        Record record = new Record(name, lastName, phone, email, address, desc);
        DatabaseHandler db_handler = new DatabaseHandler(
                context,
                Constant.DB_NAME,
                null,
                Constant.VERSION
        );

        db_handler.updateContactRecord(id, record);
        db_handler.close();
    }
}
