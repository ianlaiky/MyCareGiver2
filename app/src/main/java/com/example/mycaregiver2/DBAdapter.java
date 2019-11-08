package com.example.mycaregiver2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    private static final String TAG = "DBAdapter";

    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DOCTOR = "doctor";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_TABLETAMOUNT = "amount";
    private static final String DATABASE_NAME = "MyCareGiver_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String APPOINTMENT_DATABASE_CREATE =
            "create table Appointment (id integer primary key autoincrement,"
                    + KEY_NAME + " text not null, "
                    + KEY_TYPE + " text not null, "
                    + KEY_DOCTOR + "text not null,"
                    + KEY_DATE + "date not null,"
                    + KEY_TIME + "time not null,"
                    + KEY_VENUE + "text not null);";

    private static final String MEDICATION_DATABASE_CREATE =
            "create table Medication (id integer primary key autoincrement,"
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
            db.execSQL("DROP TABLE IF EXISTS contacts");
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

//    // ---insert a contact into the database---
//    public void insertContact(String name, String contact) {
//        String sqlStmt = "Insert into " + DATABASE_TABLE + " (" + KEY_NAME
//                + "," + KEY_CONTACT + ") VALUES ('" + name + "','" + contact
//                + "')";
//        db.execSQL(sqlStmt);
//    }// insertContact
    // TO DO
}