package com.example.mycaregiver2.Objects;

import android.content.Context;
import android.database.Cursor;


import com.example.mycaregiver2.DBAdapters.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class Appointments {
    private int appt_id;
    private String name;
    private String type;
    private String doctor;
    private String date;
    private String time;
    private String venue;

    public Appointments() {
    }

    public Appointments(int appt_id, String name, String type, String doctor, String date, String time, String venue) {
        this.appt_id = appt_id;
        this.name = name;
        this.type = type;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }

    public Appointments(String name, String type, String doctor, String date, String time, String venue) {
        this.name = name;
        this.type = type;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }


    public long addToDatabase(Appointments obj, Context c) {
        System.out.println("ADDIng to appt db");
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();

        long rowIDoInsertedEntry = db.insertEntry_Appt(obj.getName(), obj.getType(), obj.getDoctor(), obj.getDate(), obj.getTime(), obj.getVenue());
        db.close();
        return rowIDoInsertedEntry;
    }

    public ArrayList<Appointments> retireveAll(Context c) {
        System.out.println("DB APPT RETRIEVAL");
        ArrayList<Appointments> listAppt = new ArrayList<>();

        Cursor myCursor;
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();
        myCursor = db.retrieveAllAPPTEntriesCursor();
//        System.out.println(myCursor.getCount());

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();



            do {




                int key_id = Integer.parseInt(myCursor.getString(MyDbAdapter.COLUMN_KEY_ID));
                String keyname = (myCursor.getString(MyDbAdapter.COLUMN_KEY_NAME));
                String key_type = (myCursor.getString(MyDbAdapter.COLUMN_KEY_TYPE));
                String key_doctor = (myCursor.getString(MyDbAdapter.COLUMN_KEY_DOCTOR));
                String key_date = (myCursor.getString(MyDbAdapter.COLUMN_KEY_DATE));
                String key_time = (myCursor.getString(MyDbAdapter.COLUMN_KEY_TIME));
                String key_venue = (myCursor.getString(MyDbAdapter.COLUMN_KEY_VENUE));

                listAppt.add(new Appointments(key_id,keyname, key_type, key_doctor, key_date, key_time, key_venue));

            } while (myCursor.moveToNext());


        }
        return listAppt;
    }


}
