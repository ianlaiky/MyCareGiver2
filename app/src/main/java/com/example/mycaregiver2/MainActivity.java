package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycaregiver2.Objects.Appointments;
import com.example.mycaregiver2.Objects.Emen_Contact;
import com.example.mycaregiver2.Objects.Medication;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_apt_btn = findViewById(R.id.add_appt_btn);
        add_apt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_add_apt();
            }
        });

        Button add_med_btn = findViewById(R.id.add_med_btn);
        add_med_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_add_med();
            }
        });

        Button add_med_ctc = findViewById(R.id.add_eme_ctc);
        add_med_ctc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_add_ctc();
            }
        });



        Button testingbtn = findViewById(R.id.testingbtn);
        testingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointments appt = new Appointments();
                ArrayList<Appointments> listapp = appt.retireveAll(getApplicationContext());

                TextView txv = findViewById(R.id.testingtextview);
                StringBuilder allteststr = new StringBuilder();
                for (int i = 0; i < listapp.size(); i++) {
                    allteststr.append(listapp.get(i).getAppt_id());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getName());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getType());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getDoctor());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getDate());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getTime());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getVenue());
                    allteststr.append("\n");
                }
                txv.setText(allteststr);
            }
        });

        Button testbtn2 = findViewById(R.id.testingbtn2);
        testbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medication appt = new Medication();
                ArrayList<Medication> listapp = appt.retireveAll(getApplicationContext());

                TextView txv = findViewById(R.id.testingtextview);
                StringBuilder allteststr = new StringBuilder();
                for (int i = 0; i < listapp.size(); i++) {
                    allteststr.append(listapp.get(i).getMed_id());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getName());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getType());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getTime());
                    allteststr.append("\n");

                }
                txv.setText(allteststr);
            }
        });


        Button testbtn3 = findViewById(R.id.testingbtn3);
        testbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Emen_Contact appt = new Emen_Contact();
                ArrayList<Emen_Contact> listapp = appt.retireveAll(getApplicationContext());

                TextView txv = findViewById(R.id.testingtextview);
                StringBuilder allteststr = new StringBuilder();
                for (int i = 0; i < listapp.size(); i++) {
                    allteststr.append(listapp.get(i).getCtc_id());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getName());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getNumber());
                    allteststr.append("\n");
                    allteststr.append(listapp.get(i).getDescription());
                    allteststr.append("\n");

                }
                txv.setText(allteststr);
            }
        });

        Button bt = findViewById(R.id.testingbtn4);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_add_bt();
            }
        });

        alarmSet();

    }

    public void openActivity_add_apt() {

        Intent intent = new Intent(this, add_appt.class);
        startActivity(intent);
    }

    public void openActivity_add_med() {

        Intent intent = new Intent(this, add_med.class);
        startActivity(intent);
    }
    public void openActivity_add_ctc() {

        Intent intent = new Intent(this, add_ctc.class);
        startActivity(intent);
    }
    public void openActivity_add_bt() {

        Intent intent = new Intent(this, act_bt.class);
        startActivity(intent);
    }

    public void alarmSet(){

        System.out.println("ALARM RUNN");

    }
}
