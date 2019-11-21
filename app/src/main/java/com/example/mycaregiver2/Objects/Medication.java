package com.example.mycaregiver2.Objects;

import android.content.Context;
import android.database.Cursor;

import com.example.mycaregiver2.DBAdapters.MyDbAdapter;

import java.util.ArrayList;

public class Medication {

    private int med_id;
    private String name;
    private String type;
    private String time;


    public Medication() {
    }

    public Medication(int med_id, String name, String type, String time) {
        this.med_id = med_id;
        this.name = name;
        this.type = type;
        this.time = time;
    }

    public Medication(String name, String type, String time) {
        this.name = name;
        this.type = type;
        this.time = time;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long addToDatabase(Medication obj, Context c) {
        System.out.println("ADDIng to med db");
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();

        long rowIDoInsertedEntry = db.insertEntry_Med(obj.getName(), obj.getType(), obj.getTime());
        db.close();
        return rowIDoInsertedEntry;
    }

    public ArrayList<Medication> retireveAll(Context c) {
//        System.out.println("DB MED RETRIEVAL");
        ArrayList<Medication> listAppt = new ArrayList<>();

        Cursor myCursor;
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();
        myCursor = db.retrieveAllMEDEntriesCursor();
//        System.out.println(myCursor.getCount());

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();


            do {


                int key_id = Integer.parseInt(myCursor.getString(MyDbAdapter.COLUMN_KEY_ID_MED));
                String keyname = (myCursor.getString(MyDbAdapter.COLUMN_KEY_NAME_MED));
                String key_type = (myCursor.getString(MyDbAdapter.COLUMN_KEY_TYPE_MED));
                String key_time = (myCursor.getString(MyDbAdapter.COLUMN_KEY_TIME_MED));


                listAppt.add(new Medication(key_id,keyname,key_type,key_time));

            } while (myCursor.moveToNext());


        }
        return listAppt;
    }

}
