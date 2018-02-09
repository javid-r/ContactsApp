package com.exercises.javid.contactsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Javid on 1/28/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(Context context, String db_name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, db_name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + Constant.TABLE_NAME +
                " (\n\t" + Constant.T_ID + "\tINTEGER" + "\tPRIMARY KEY" +
                " ,\n\t" + Constant.T_NAME + "\tTEXT" +
                " ,\n\t" + Constant.T_L_NAME + "\tTEXT" +
                " ,\n\t" + Constant.T_PHONE + "\tTEXT" +
                " ,\n\t" + Constant.T_EMAIL + "\tTEXT" +
                " ,\n\t" + Constant.T_ADDRESS + "\tTEXT" +
                " ,\n\t" + Constant.T_DESC + "\tTEXT" +
                " ,\n\t" + Constant.T_DATE + "\tTEXT" +
                "\n ); ";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + Constant.TABLE_NAME + ";";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    void addContactRecord(Record record) {

        String addContactRecordDate = getDateString(context);

        String addContactRecordQuery = "INSERT INTO " + Constant.TABLE_NAME +
                " ( " + Constant.T_NAME +
                " , " + Constant.T_L_NAME +
                " , " + Constant.T_PHONE +
                " , " + Constant.T_EMAIL +
                " , " + Constant.T_ADDRESS +
                " , " + Constant.T_DESC +
                " , " + Constant.T_DATE +
                " )\n " + "VALUES" +
                " (\n\t'" + record.getName() +
                "' ,\n\t'" + record.getLastName() +
                "' ,\n\t'" + record.getPhone() +
                "' ,\n\t'" + record.getEmail() +
                "' ,\n\t'" + record.getAddress() +
                "' ,\n\t'" + record.getDesc() +
                "' ,\n\t'" + addContactRecordDate +
                "'\n ); ";

        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            db.execSQL(addContactRecordQuery);
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
                    Constant.TABLE_NAME,
                    new String[]{
                            Constant.T_ID,
                            Constant.T_NAME,
                            Constant.T_L_NAME,
                            Constant.T_DATE
                    },
                    null, null, null, null,
                    Constant.T_DATE /*+ " DESC"*/
            );

            if (cursor.moveToFirst()) {
                for (int counter = 0; counter < cursor.getCount(); counter++) {
                    HashMap<String, String> record = new HashMap<>();
                    record.put(Constant.T_ID, cursor.getInt(cursor.getColumnIndex(Constant.T_ID)) + "");
                    record.put(Constant.T_NAME, cursor.getString(cursor.getColumnIndex(Constant.T_NAME)));
                    record.put(Constant.T_L_NAME, cursor.getString(cursor.getColumnIndex(Constant.T_L_NAME)));
                    record.put(Constant.T_DATE, cursor.getString(cursor.getColumnIndex(Constant.T_DATE)));
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
        String selection = Constant.T_ID + " = " + t_ID;

        try {
            db = this.getReadableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(
                    Constant.TABLE_NAME,
                    new String[]{
                            Constant.T_NAME,
                            Constant.T_L_NAME,
                            Constant.T_PHONE,
                            Constant.T_EMAIL,
                            Constant.T_ADDRESS,
                            Constant.T_DESC
                    },
                    selection,
                    null, null, null, null
            );

            if (cursor.moveToFirst()) {
                record.put(Constant.T_NAME, cursor.getString(cursor.getColumnIndex(Constant.T_NAME)));
                record.put(Constant.T_L_NAME, cursor.getString(cursor.getColumnIndex(Constant.T_L_NAME)));
                record.put(Constant.T_PHONE, cursor.getString(cursor.getColumnIndex(Constant.T_PHONE)));
                record.put(Constant.T_EMAIL, cursor.getString(cursor.getColumnIndex(Constant.T_EMAIL)));
                record.put(Constant.T_ADDRESS, cursor.getString(cursor.getColumnIndex(Constant.T_ADDRESS)));
                record.put(Constant.T_DESC, cursor.getString(cursor.getColumnIndex(Constant.T_DESC)));
            }
            cursor.close();
            db.close();
        }

        return record;
    }

    void deleteContactRecord(String t_ID) {


        String deleteRecordQuery = "DELETE FROM " + Constant.TABLE_NAME +
                " WHERE " + Constant.T_ID + " = '" + t_ID + "';";

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

    void updateContactRecord(String t_ID, Record record) {

        String updateContactRecordDate = getDateString(context);

            String updateContactRecordQuery = "UPDATE " + Constant.TABLE_NAME + " SET " +
                    "   \n\t"   + Constant.T_NAME +
                    " = '"      + record.getName() +
                    "' ,\n\t"   + Constant.T_L_NAME +
                    " = '"      + record.getLastName() +
                    "' ,\n\t"   + Constant.T_PHONE +
                    " = '"      + record.getPhone() +
                    "' ,\n\t"   + Constant.T_EMAIL +
                    " = '"      + record.getEmail() +
                    "' ,\n\t"   + Constant.T_ADDRESS +
                    " = '"      + record.getAddress() +
                    "' ,\n\t"   + Constant.T_DESC +
                    " = '"      + record.getDesc() +
                    "' ,\n\t"   + Constant.T_DATE +
                    " = '"      + updateContactRecordDate +
                    "' \n\t"    + "WHERE" +
                    "  \n\t"    + Constant.T_ID +
                    " = '"      + t_ID +
                    "';";

        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }

        if (db != null && db.isOpen()) {
            db.execSQL(updateContactRecordQuery);
            db.close();
        }
    }

    String getDateString(Context context){
        long dateLong = new Date(java.lang.System.currentTimeMillis()).getTime();
        java.text.DateFormat dateFormatter = android.text.format.DateFormat.getDateFormat(context);
        return dateFormatter.format(dateLong);
    }
}
