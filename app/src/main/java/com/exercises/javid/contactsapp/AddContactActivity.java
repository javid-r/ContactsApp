package com.exercises.javid.contactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private EditText address;
    private EditText desc;

    private Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        name = findViewById(R.id.edit_add_name);
        lastName = findViewById(R.id.edit_add_last_name);
        phone = findViewById(R.id.edit_add_phone);
        email = findViewById(R.id.edit_add_email);
        address = findViewById(R.id.edit_add_address);
        desc = findViewById(R.id.edit_add_desc);
        ((Button) findViewById(R.id.btn_add_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
                name.setText("");
                lastName.setText("");
                phone.setText("");
                email.setText("");
                address.setText("");
                desc.setText("");
            }
        });

    }

    private void saveContact() {
        String name = this.name.getText().toString();
        String lastName = this.lastName.getText().toString();
        String phone = this.phone.getText().toString();
        String email = this.email.getText().toString();
        String address = this.address.getText().toString();
        String desc = this.desc.getText().toString();

        Record record = new Record(name, lastName, phone, email, address, desc);
        DB_Handler db_handler = new DB_Handler(context);
        db_handler.addRecord(record);

        db_handler.close();
    }

}
