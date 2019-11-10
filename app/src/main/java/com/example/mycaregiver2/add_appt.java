package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.mycaregiver2.Objects.Appointments;

public class add_appt extends AppCompatActivity {
    Menu myMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.myMenu = menu;
        getMenuInflater().inflate(R.menu.saveoptions, menu);
        return true;
    }


    //since one options, do not needd to do options selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println("Save clicked");

        EditText name = findViewById(R.id.edittext1);
        EditText type = findViewById(R.id.edittext2);
        EditText doctor = findViewById(R.id.edittext3);
        EditText date = findViewById(R.id.edittext4);
        EditText time = findViewById(R.id.edittext5);
        EditText venue = findViewById(R.id.edittext6);


        Appointments appt = new Appointments(name.getText().toString(), type.getText().toString(), doctor.getText().toString(), date.getText().toString(), time.getText().toString(), venue.getText().toString());

        appt.addToDatabase(appt,getApplicationContext());


        finish();
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appt);
    }
}
