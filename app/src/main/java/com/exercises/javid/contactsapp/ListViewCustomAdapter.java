package com.exercises.javid.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Javid on 1/28/2018.
 */

public class ListViewCustomAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> contactsList;
    private Context context;

    private LayoutInflater inflater = null;

    public ListViewCustomAdapter(Context context) {
        DB_Handler db_handler = new DB_Handler(context);
        contactsList = db_handler.getContactsList();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db_handler.close();
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> contact = contactsList.get(position);
        final String name = contact.get(DB_Handler.T_NAME) + "_" +
                contact.get(DB_Handler.T_L_NAME);
        final String phone = contact.get(DB_Handler.T_PHONE);
        final String date = contact.get(DB_Handler.T_DATE);
        final String id = contact.get(DB_Handler.T_ID);

        View rowView = inflater.inflate(R.layout.list_item, null);
        TextView _name = rowView.findViewById(R.id.txt_name);
        TextView _phone = rowView.findViewById(R.id.txt_phone);
        TextView _date = rowView.findViewById(R.id.txt_date);

        _name.setText(name);
        _phone.setText(phone);
        _date.setText(date);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Clicked " + name, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ContactInfoActivity.class);
                intent.putExtra(DB_Handler.T_ID, id);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}
