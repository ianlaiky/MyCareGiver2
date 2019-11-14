package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.mycaregiver2.Objects.Emen_Contact;
import com.example.mycaregiver2.Objects.Medication;

public class add_ctc extends AppCompatActivity {
    Menu myMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.myMenu = menu;
        getMenuInflater().inflate(R.menu.saveoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println("Save clicked");

        EditText name = findViewById(R.id.edittext1);
        EditText number = findViewById(R.id.edittext2);
        EditText desc = findViewById(R.id.edittext3);


        Emen_Contact ct = new Emen_Contact(name.getText().toString(),number.getText().toString(),desc.getText().toString());
        ct.addToDatabase(ct,getApplicationContext());



        finish();
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ctc);
    }
}
