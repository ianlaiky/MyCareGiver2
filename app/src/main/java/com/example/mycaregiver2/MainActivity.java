package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycaregiver2.Objects.Appointments;

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


    }

    public void openActivity_add_apt() {

        Intent intent = new Intent(this, add_appt.class);
        startActivity(intent);
    }
}
