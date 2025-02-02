package com.example.mycaregiver2.DBAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbAdapter {
    private static final String DATABASE_NAME = "test.db";
    private static final String DATABASE_TABLE_APPT = "Appointments";
    private static final String DATABASE_TABLE_MED = "Medication";
    private static final String DATABASE_TABLE_EMEM_CTC = "Contacts";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase _db;
    private final Context context;

    //appt
    private static final String KEY_ID = "_id";
    public static final int COLUMN_KEY_ID = 0;
    public static final String KEY_NAME = "name";
    public static final int COLUMN_KEY_NAME = 1;
    private static final String KEY_TYPE = "type";
    public static final int COLUMN_KEY_TYPE = 2;
    private static final String KEY_DOCTOR = "doctor";
    public static final int COLUMN_KEY_DOCTOR = 3;
    private static final String KEY_DATE = "date";
    public static final int COLUMN_KEY_DATE = 4;
    private static final String KEY_TIME = "time";
    public static final int COLUMN_KEY_TIME = 5;
    private static final String KEY_VENUE = "venue";
    public static final int COLUMN_KEY_VENUE = 6;

    //med
    private static final String KEY_ID_MED = "_id";
    public static final int COLUMN_KEY_ID_MED = 0;
    public static final String KEY_NAME_MED = "name";
    public static final int COLUMN_KEY_NAME_MED = 1;
    private static final String KEY_TYPE_MED = "type";
    public static final int COLUMN_KEY_TYPE_MED = 2;
    private static final String KEY_TIME_MED = "time";
    public static final int COLUMN_KEY_TIME_MED = 3;

    //ctc
    private static final String KEY_ID_CTC = "_id";
    public static final int COLUMN_KEY_ID_CTC = 0;
    public static final String KEY_NAME_CTC = "name";
    public static final int COLUMN_KEY_NAME_CTC = 1;
    private static final String KEY_NUM_CTC = "number";
    public static final int COLUMN_KEY_TYPE_CTC = 2;
    private static final String KEY_DES_CTC = "description";
    public static final int COLUMN_KEY_DES_CTC = 3;

    protected static final String DATABASE_CREATE_APPT = "create table "
            + DATABASE_TABLE_APPT + " " + "("
            + KEY_ID + " integer primary key autoincrement, "
            + KEY_NAME + " not null, "
            + KEY_TYPE + " not null, "
            + KEY_DOCTOR + " not null, "
            + KEY_DATE + " not null, "
            + KEY_TIME + " not null, "
            + KEY_VENUE + " not null);";

    protected static final String DATABASE_CREATE_MED = "create table "
            + DATABASE_TABLE_MED + " " + "("
            + KEY_ID_MED + " integer primary key autoincrement, "
            + KEY_NAME_MED + " not null, "
            + KEY_TYPE_MED + " not null, "
            + KEY_TIME_MED + " not null);";

    protected static final String DATABASE_CREATE_CTC = "create table "
            + DATABASE_TABLE_EMEM_CTC + " " + "("
            + KEY_ID_CTC + " integer primary key autoincrement, "
            + KEY_NAME_CTC + " not null, "
            + KEY_NUM_CTC + " not null, "
            + KEY_DES_CTC + " not null);";

    private String MYDBADAPTER_LOG_CAT = "MY_LOG";

    private MyDBOpenHelper dbHelper;

    public MyDbAdapter(Context _context) {
        this.context = _context;
        dbHelper = new MyDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);


        //step 16 - create MyDBOpenHelper object


    }

    public void close() {

        _db.close();
        Log.w(MYDBADAPTER_LOG_CAT, "DB closed");
        //step 17 - close db

    }

    public void open() throws SQLiteException {

        try {
            _db = dbHelper.getWritableDatabase();
            Log.w(MYDBADAPTER_LOG_CAT, "DB closed");
        } catch (SQLiteException e) {

            _db = dbHelper.getWritableDatabase();
            Log.w(MYDBADAPTER_LOG_CAT, "DB opened as readable database");

        }
        //step 18 - open db


    }

    public long insertEntry_Appt(String name, String type, String doctor, String date, String time, String venue) {
        ContentValues newEntryValues = new ContentValues();

        newEntryValues.put(KEY_NAME, name);
        newEntryValues.put(KEY_TYPE, type);
        newEntryValues.put(KEY_DOCTOR, doctor);
        newEntryValues.put(KEY_DATE, date);
        newEntryValues.put(KEY_TIME, time);
        newEntryValues.put(KEY_VENUE, venue);

        Log.w(MYDBADAPTER_LOG_CAT, "Inserted Codes = " + name + type + doctor + date + time + venue);

        return _db.insert(DATABASE_TABLE_APPT, null, newEntryValues);
        //step 19 - insert record into table

    }

    public long insertEntry_Med(String name, String type, String time) {
        ContentValues newEntryValues = new ContentValues();

        newEntryValues.put(KEY_NAME_MED, name);
        newEntryValues.put(KEY_TYPE_MED, type);
        newEntryValues.put(KEY_TIME_MED, time);


        Log.w(MYDBADAPTER_LOG_CAT, "Inserted Codes = " + name + type + time);

        return _db.insert(DATABASE_TABLE_MED, null, newEntryValues);
        //step 19 - insert record into table

    }
    public long insertEntry_CTCe(String name, String number, String desc) {

        ContentValues newEntryValues = new ContentValues();

        newEntryValues.put(KEY_NAME_CTC, name);
        newEntryValues.put(KEY_NUM_CTC, number);
        newEntryValues.put(KEY_DES_CTC, desc);


        Log.w(MYDBADAPTER_LOG_CAT, "Inserted Codes = " + name + number + desc);

        return _db.insert(DATABASE_TABLE_EMEM_CTC, null, newEntryValues);
        //step 19 - insert record into table

    }

    public boolean removeEntry_appt(long _rowIndex) {

        if (_db.delete(DATABASE_TABLE_APPT, KEY_ID + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Failed");
            return false;
        }
        Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Success");
        //step 20 - remove record from table


        return true;

    }

    public boolean removeEntry_Med(long _rowIndex) {

        if (_db.delete(DATABASE_TABLE_MED, KEY_ID_MED + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Failed");
            return false;
        }
        Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Success");
        //step 20 - remove record from table


        return true;

    }

    public boolean removeEntry_CTC(long _rowIndex) {

        if (_db.delete(DATABASE_TABLE_EMEM_CTC, KEY_ID_CTC + "=" + _rowIndex, null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Failed");
            return false;
        }
        Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = " + _rowIndex + "Success");
        //step 20 - remove record from table


        return true;

    }

    public boolean updateEntry_appt(long _rowIndex, String name, String type, String doctor, String date, String time, String venue) {
        ContentValues cv = new ContentValues();

        //These Fields should be your String values of actual column names
        cv.put(KEY_NAME, name);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_DOCTOR, doctor);
        cv.put(KEY_DATE, date);
        cv.put(KEY_TIME, time);
        cv.put(KEY_VENUE, venue);

        _db.update(DATABASE_TABLE_APPT, cv, "_id=" + _rowIndex, null);

        return false;
    }

    public boolean updateEntry_Med(long _rowIndex, String name, String type, String time) {
        ContentValues cv = new ContentValues();

        //These Fields should be your String values of actual column names
        cv.put(KEY_NAME, name);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_TIME, time);


        _db.update(DATABASE_TABLE_MED, cv, "_id=" + _rowIndex, null);

        return false;
    }

    public boolean updateEntry_CTC(long _rowIndex, String name, String number, String desc) {
        ContentValues cv = new ContentValues();

        //These Fields should be your String values of actual column names
        cv.put(KEY_NAME, name);
        cv.put(KEY_TYPE, number);
        cv.put(KEY_TIME, desc);


        _db.update(DATABASE_TABLE_EMEM_CTC, cv, "_id=" + _rowIndex, null);

        return false;
    }

    public Cursor retrieveAllAPPTEntriesCursor() {

        Cursor c = null;

        try {
            c = _db.query(DATABASE_TABLE_APPT, new String[]{KEY_ID, KEY_NAME, KEY_TYPE, KEY_DOCTOR, KEY_DATE, KEY_TIME, KEY_VENUE},
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.w(MYDBADAPTER_LOG_CAT, "Retrieve fail!");

        }

        return c;
        //step 21 - retrieve all records from table
    }

    public Cursor retrieveAllMEDEntriesCursor() {

        Cursor c = null;

        try {
            c = _db.query(DATABASE_TABLE_MED, new String[]{KEY_ID_MED, KEY_NAME_MED, KEY_TYPE_MED, KEY_TIME_MED},
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.w(MYDBADAPTER_LOG_CAT, "Retrieve fail!");

        }

        return c;
        //step 21 - retrieve all records from table
    }

    public Cursor retrieveAllCTCEntriesCursor() {

        Cursor c = null;

        try {
            c = _db.query(DATABASE_TABLE_EMEM_CTC, new String[]{KEY_ID_CTC, KEY_NAME_CTC, KEY_NUM_CTC, KEY_DES_CTC},
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.w(MYDBADAPTER_LOG_CAT, "Retrieve fail!");

        }

        return c;
        //step 21 - retrieve all records from table
    }

    public class MyDBOpenHelper extends SQLiteOpenHelper {
        public MyDBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL(DATABASE_CREATE_APPT);
            db.execSQL(DATABASE_CREATE_MED);
            db.execSQL(DATABASE_CREATE_CTC);
            Log.w(MYDBADAPTER_LOG_CAT, "Helper : DB " + DATABASE_TABLE_APPT + "Created!!");

            //step 14 - execute create sql statement


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    } // End of myDBOpenHelper

}
