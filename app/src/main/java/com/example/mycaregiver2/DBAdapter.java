package com.example.mycaregiver2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.sql.Time;

public class DBAdapter {

    //Still in development, I have no clue why it doesn't create a database yet, to be continued  - Guang Jun//
    private static final String TAG = "DBAdapter";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DOCTOR = "doctor";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_TABLETAMOUNT = "amount";
    private static final String DATABASE_NAME = "MyCareGiver_DB";
    private static final String APPOINTMENT_TABLE = "Appointment";
    private static final String MEDICATTION_TABLE = "Medication";
    private static final int DATABASE_VERSION = 1;

    private static final String APPOINTMENT_DATABASE_CREATE =
            "create table " + APPOINTMENT_TABLE + " (id integer primary key autoincrement,"
                    + KEY_NAME + " text not null, "
                    + KEY_TYPE + " text not null, "
                    + KEY_DOCTOR + "text not null,"
                    + KEY_DATE + "date not null,"
                    + KEY_TIME + "time not null,"
                    + KEY_VENUE + "text not null);";

    private static final String MEDICATION_DATABASE_CREATE =
            "create table " + MEDICATTION_TABLE + " (id integer primary key autoincrement,"
                    + KEY_NAME + " text not null, "
                    + KEY_TABLETAMOUNT + "integer not null, "
                    + KEY_TIME + " time not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(APPOINTMENT_DATABASE_CREATE);
                db.execSQL(MEDICATION_DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.d(TAG, "Upgrading database from version " +
                    oldVersion + " to " + newVersion +
                    ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Appointment");
            db.execSQL("DROP TABLE IF EXISTS Medication");
            onCreate(db);
        }
    } // DatabaseHelper

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }// open

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }// close

    // ---insert a appointment into the database---
    public void insertAppointment(String name, String type, String doctor, Date date, Time time, String venue) {
        String sqlStmt = "Insert into " + APPOINTMENT_TABLE + " (" + KEY_NAME
                + "," + KEY_TYPE + "," + KEY_DOCTOR + "," + KEY_DATE + "," + KEY_TIME + "," + KEY_VENUE + ") VALUES ('" + name + "','" + type
                + "','" + doctor + "','" + date + "','" + time + "','" + venue + "')";
        db.execSQL(sqlStmt);
    }

    public void insertMedication(String name, Integer amount, Time time) {
        String sqlStmt = "Insert into " + MEDICATTION_TABLE + " (" + KEY_NAME
                + "," + KEY_TABLETAMOUNT + "," + KEY_TIME + ") VALUES ('" + name + "','" + amount
                + "','" + time + "')";
        db.execSQL(sqlStmt);
    }
}
