package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import 	androidx.core.app.NotificationCompat;
import 	android.app.NotificationManager;
import 	android.app.NotificationChannel;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.example.mycaregiver2.Objects.Appointments;
import com.example.mycaregiver2.Objects.Emen_Contact;
import com.example.mycaregiver2.Objects.Medication;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

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

        //Sync Time
        Button btnbtsenddat = findViewById(R.id.testingbtn5);
        btnbtsenddat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentDate = "D" + new SimpleDateFormat("yyyy MM dd k m s").format(new Date());
                Bluetooth.sendData(currentDate);
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

        createNotificationChannel();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        sleep(5000);
                        alarmSetMed();
                        alarmSetApt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
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
    private static final String PRIMARY_CHANNEL_ID = "Caregiver";
    private NotificationManager mNotifyManager;
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Caregiver Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification for Caregiver");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    private static final int NOTIFICATION_ID = 0;
    public void alarmSetMed(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(currentTime);
        Medication medObj = new Medication();
        ArrayList<Medication> arrMed = medObj.retireveAll(getApplicationContext());
        System.out.println("Current Time: "+strDate);

        for (int s = 0; s < arrMed.size(); s++) {
            System.out.println("Medicine #"+s+":");
            if (strDate.equals(arrMed.get(s).getTime())){
                System.out.println("Medicine #"+s+" hit!: "+strDate);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Medication notification")
                        .setContentText("EAT YOUR MEDICINE"+arrMed.get(s).getType()+arrMed.get(s).getName())
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(arrMed.get(s).getName()+" it is time to eat " + arrMed.get(s).getType()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                androidx.core.app.NotificationManagerCompat notificationManager = androidx.core.app.NotificationManagerCompat.from(this);
                notificationManager.notify(1001, builder.build());

                try{
                    Bluetooth.sendData("Meds: " + arrMed.get(s).getTime());
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }
    }
    public void alarmSetApt(){
        // 2 Hour Before Notifier
        Calendar cal = Calendar.getInstance(); // creates calendar
        Date currentTime1 = cal.getTime(); // returns new date object
        cal.setTime(currentTime1); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, +2); // plus 2 hour

        // 1 Day Before Notifier
        Calendar caloneday = Calendar.getInstance(); // creates calendar
        Date currentTime2 = caloneday.getTime(); // returns new date object
        caloneday.setTime(currentTime2); // sets calendar time/date
        caloneday.add(Calendar.DATE, +1); // plus 2 hour


        Appointments aptObj = new Appointments();
        ArrayList<Appointments> arrApt = aptObj.retireveAll(getApplicationContext());

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate1 = dateFormat.format(cal.getTime());
        String strDate2 = dateFormat.format(caloneday.getTime());
        System.out.println("'2 Hours Later' time to check: "+strDate1);
        System.out.println("'1 Day Later' day to check: "+strDate2);

        for (int s = 0; s < arrApt.size(); s++) {
            System.out.println("Appointment #"+s+":");
            System.out.println ("Appointment: "+arrApt.get(s).getDate() + " "+arrApt.get(s).getTime());
            if (strDate1.equals(arrApt.get(s).getDate() + " "+arrApt.get(s).getTime())){
                System.out.println("Appointment #"+s+" for 2 Hours later hit!: "+strDate1);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Appointment notification")
                        .setContentText("See doctor"+arrApt.get(s).getType()+arrApt.get(s).getName())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(arrApt.get(s).getName()+" remember to see doctor " + arrApt.get(s).getType()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                androidx.core.app.NotificationManagerCompat notificationManager = androidx.core.app.NotificationManagerCompat.from(this);
                notificationManager.notify(1002, builder.build());
                try{
                    Bluetooth.sendData("Appt: " + arrApt.get(s).getTime());
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
            else if (strDate2.equals(arrApt.get(s).getDate() + " "+arrApt.get(s).getTime())){
                System.out.println("Appointment #"+s+" for 1 Day later hit!: "+strDate2);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Appointment notification")
                        .setContentText("See doctor"+arrApt.get(s).getType()+arrApt.get(s).getName())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(arrApt.get(s).getName()+" remember to see doctor " + arrApt.get(s).getType()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                androidx.core.app.NotificationManagerCompat notificationManager = androidx.core.app.NotificationManagerCompat.from(this);
                notificationManager.notify(1002, builder.build());
                try{
                    Bluetooth.sendData("Appt: " + arrApt.get(s).getTime());
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmSetMed();
        alarmSetApt();
    }
}
