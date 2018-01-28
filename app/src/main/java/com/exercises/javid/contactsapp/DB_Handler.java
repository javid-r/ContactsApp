package com.exercises.javid.contactsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Javid on 1/28/2018.
 */

public class DB_Handler extends SQLiteOpenHelper {

    final static String DB_NAME = "mySQLiteDatabase";
    final static String TABLE_NAME = "contacts";
    final static String T_ID = "_id";
    final static String T_NAME = "f_name";
    final static String T_L_NAME = "l_name";
    final static String T_PHONE = "phone";
    final static String T_EMAIL = "email";
    final static String T_ADDRESS = "address";
    final static String T_DESC = "descriptions";
    final static String T_DATE = "date";

    private final Context context;

    public DB_Handler(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (\n\t" + T_ID + "\tINTEGER" + "\tPRIMARY KEY" +
                " ,\n\t" + T_NAME + "\tTEXT" +
                " ,\n\t" + T_L_NAME + "\tTEXT" +
                " ,\n\t" + T_PHONE + "\tTEXT" +
                " ,\n\t" + T_EMAIL + "\tTEXT" +
                " ,\n\t" + T_ADDRESS + "\tTEXT" +
                " ,\n\t" + T_DESC + "\tTEXT" +
                " ,\n\t" + T_DATE + "\tTEXT" +
                "\n ); ";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    void addRecord(Record record) {

        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String addRecordDate = dateFormat.format(new Date(java.lang.System.currentTimeMillis()).getTime());

        String addRecordQuery = "INSERT INTO " + TABLE_NAME +
                " ( " + T_NAME +
                " , " + T_L_NAME +
                " , " + T_PHONE +
                " , " + T_EMAIL +
                " , " + T_ADDRESS +
                " , " + T_DESC +
                " , " + T_DATE +
                " )\n " + "VALUES" +
                " (\n\t'" + record.getName() +
                "' ,\n\t'" + record.getLastName() +
                "' ,\n\t'" + record.getPhone() +
                "' ,\n\t'" + record.getEmail() +
                "' ,\n\t'" + record.getAddress() +
                "' ,\n\t'" + record.getDesc() +
                "' ,\n\t'" + addRecordDate +
                "'\n ); ";

        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            db.execSQL(addRecordQuery);
            db.close();
        }
    }

    ArrayList<HashMap<String, String>> getContactsList() {
        ArrayList<HashMap<String, String>> contactsList = new ArrayList<>();

        SQLiteDatabase db = null;

        try {
            db = this.getReadableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(
                    TABLE_NAME, new String[]{T_ID, T_NAME, T_L_NAME, T_DATE},
                    null, null, null, null,
                    T_DATE + " DESC"
            );

            if (cursor.moveToFirst()) {
                for (int counter = 0; counter < cursor.getCount(); counter++) {
                    HashMap<String, String> record = new HashMap<>();
                    record.put(T_ID, cursor.getInt(cursor.getColumnIndex(T_ID)) + "");
                    record.put(T_NAME, cursor.getString(cursor.getColumnIndex(T_NAME)));
                    record.put(T_L_NAME, cursor.getString(cursor.getColumnIndex(T_L_NAME)));
                    record.put(T_DATE, cursor.getString(cursor.getColumnIndex(T_DATE)));
                    contactsList.add(record);
                    cursor.moveToNext();
                }
            }

            cursor.close();
            db.close();
        }

        return contactsList;
    }

    HashMap<String, String> getContactRecord(String t_ID) {

        HashMap<String, String> record = new HashMap<>();
        SQLiteDatabase db = null;
        String selection = T_ID + " = " + t_ID;

        try {
            db = this.getReadableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(
                    TABLE_NAME,
                    new String[]{T_NAME, T_L_NAME, T_PHONE, T_EMAIL, T_ADDRESS, T_DESC},
                    selection,
                    null, null, null, null
            );

            if (cursor.moveToFirst()) {
                record.put(T_NAME, cursor.getString(cursor.getColumnIndex(T_NAME)));
                record.put(T_L_NAME, cursor.getString(cursor.getColumnIndex(T_L_NAME)));
                record.put(T_PHONE, cursor.getString(cursor.getColumnIndex(T_PHONE)));
                record.put(T_EMAIL, cursor.getString(cursor.getColumnIndex(T_EMAIL)));
                record.put(T_ADDRESS, cursor.getString(cursor.getColumnIndex(T_ADDRESS)));
                record.put(T_DESC, cursor.getString(cursor.getColumnIndex(T_DESC)));
            }
            cursor.close();
            db.close();
        }

        return record;
    }

    void deleteContactRecord(String t_ID) {


        String deleteRecordQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + T_ID + " = '" + t_ID + "';";

        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            db.execSQL(deleteRecordQuery);
            db.close();
        }
    }

    void editContactRecord(Record record, String t_ID) {

    }
}
