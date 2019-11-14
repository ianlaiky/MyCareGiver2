package com.example.mycaregiver2.Objects;

import android.content.Context;
import android.database.Cursor;

import com.example.mycaregiver2.DBAdapters.MyDbAdapter;

import java.util.ArrayList;

public class Emen_Contact {

    private int ctc_id;
    private String name;
    private String number;
    private String description;

    public Emen_Contact() {
    }

    public Emen_Contact(String name, String number, String description) {
        this.name = name;
        this.number = number;
        this.description = description;
    }

    public Emen_Contact(int ctc_id, String name, String number, String description) {
        this.ctc_id = ctc_id;
        this.name = name;
        this.number = number;
        this.description = description;
    }

    public int getCtc_id() {
        return ctc_id;
    }

    public void setCtc_id(int ctc_id) {
        this.ctc_id = ctc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long addToDatabase(Emen_Contact obj, Context c) {
        System.out.println("ADDIng to ctc db");
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();

        long rowIDoInsertedEntry = db.insertEntry_CTCe(obj.getName(), obj.getNumber(), obj.getDescription());
        db.close();
        return rowIDoInsertedEntry;
    }



    public ArrayList<Emen_Contact> retireveAll(Context c) {
        System.out.println("DB ctc RETRIEVAL");
        ArrayList<Emen_Contact> listAppt = new ArrayList<>();

        Cursor myCursor;
        MyDbAdapter db = new MyDbAdapter(c);
        db.open();
        myCursor = db.retrieveAllCTCEntriesCursor();

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();


            do {


                int key_id = Integer.parseInt(myCursor.getString(MyDbAdapter.COLUMN_KEY_ID_CTC));
                String keyname = (myCursor.getString(MyDbAdapter.COLUMN_KEY_NAME_CTC));
                String key_num = (myCursor.getString(MyDbAdapter.COLUMN_KEY_TYPE_CTC));
                String key_desc = (myCursor.getString(MyDbAdapter.COLUMN_KEY_DES_CTC));


                listAppt.add(new Emen_Contact(key_id,keyname,key_num,key_desc));

            } while (myCursor.moveToNext());


        }
        return listAppt;
    }

}


