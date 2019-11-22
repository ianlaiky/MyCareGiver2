package com.example.mycaregiver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycaregiver2.Objects.Emen_Contact;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;


public class act_bt extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private String lon="0";
    private String lat="0";
    private String deviceNameToConnect;
    private BluetoothGatt mBluetoothGatt;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button startScanningButton;
    Button stopScanningButton;
    TextView peripheralTextView;
    public final static String DEVICE_DOES_NOT_SUPPORT_UART =
            "com.example.bluetooth.le.DEVICE_DOES_NOT_SUPPORT_UART";
    public static final UUID RX_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static final String RX_SERVICE_UUID_String = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_SMS = 1;
    public static final UUID RX_CHAR_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_bt);


        peripheralTextView = findViewById(R.id.PeripheralTextView);
        peripheralTextView.setMovementMethod(new ScrollingMovementMethod());

        startScanningButton = findViewById(R.id.StartScanButton);
        startScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText devconne = findViewById(R.id.devnameedittext);
                String devconstr = String.valueOf(devconne.getText());
                deviceNameToConnect = devconstr;
                startScanning();
            }
        });

        stopScanningButton = findViewById(R.id.StopScanButton);
        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopScanning();
            }
        });
        stopScanningButton.setVisibility(View.INVISIBLE);

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);

            return;
        }








        //location services
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        System.out.println("LOCATIOJ RUN");

        // fused services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lon = String.valueOf(location.getLongitude());
                            lat = String.valueOf(location.getLatitude());
                            TextView ed = findViewById(R.id.loglat);
                            ed.setText(lon + ":" + lat);
                        }
                    }
                });

        //end location services
        final Button sendDataBtn = findViewById(R.id.senddatabtn);
        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edittextData = findViewById(R.id.senddata);
                String strData = String.valueOf(edittextData.getText());
                sendData(strData);
//                BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID); //showMessage("mBluetoothGatt null"+ mBluetoothGatt);
//                BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(RX_SERVICE_UUID);
//                readCharacteristic(RxChar);

            }
        });


    }

    // connect to gatt
    public void gattConnect(BluetoothDevice device) {
        System.out.println("GATT RUN");
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);

    }

//    private final LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//           lon = String.valueOf(location.getLongitude());
//           lat = String.valueOf(location.getLatitude());
//
//
//            TextView ed = findViewById(R.id.loglat);
//            ed.setText(lon + ":" + lat);
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {
//            System.out.println("LOcation status changed");
//        }
//
//        @Override
//        public void onProviderEnabled(String s) {
//            System.out.println("on provider enabled");
//        }
//
//        @Override
//        public void onProviderDisabled(String s) {
//            System.out.println("onprovider diabled");
//        }
//
//
//    };
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            System.out.println("MTU TEST");
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            String intentAction;
            if (newState == STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                broadcastUpdate(intentAction);
                System.out.println("Connected to GATT server.");
                System.out.println("Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());


            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                System.out.println("Disconnected from GATT server.");
                broadcastUpdate(intentAction);
                mBluetoothGatt.close();
                mBluetoothGatt.disconnect();
            }
        }

        @Override
        // New services discovered
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            System.out.println("Service discovered to scan for service");
            for (BluetoothGattService gattService : mBluetoothGatt.getServices()) {
                System.out.println("Service UUID Found: " + gattService.getUuid().toString());
            }
            if (status == BluetoothGatt.GATT_SUCCESS) {
                BluetoothGattService mBluetoothGattService = mBluetoothGatt.getService(UUID.fromString(RX_SERVICE_UUID_String));
                if (mBluetoothGattService != null) {
                    System.out.println("Service characteristic UUID found: " + mBluetoothGattService.getUuid().toString());
//                    String str = "SDASD";
//                    sendData(str);
                    Bluetooth.setmBluetoothGatt(mBluetoothGatt);
                    enableTXNotification();
                } else {
                    System.out.println("Service characteristic not found for UUID: " + RX_SERVICE_UUID);
                }

            }

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            System.out.println("FSDF");
//            enableTXNotification();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                System.out.println("sdfsd");

//                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            System.out.println("ASD");


            byte[] ar = new byte[characteristic.getValue().length - 2];
            for (int i = 0; i < characteristic.getValue().length - 2; i++) {

                ar[i] = characteristic.getValue()[i];
            }


            String s = new String(ar, StandardCharsets.US_ASCII);
            System.out.println(s);

            if (s.equals("999")) {
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String strDate = dateFormat.format(currentTime);
                System.out.println(strDate);

                String name = "James";
                String location = lat+","+lon;

                String caturl = "https://mycaregiver.herokuapp.com/api/new?" + "name=" + name + "&time=" + strDate + "&location=" + location;


                URL url = null;
                try {
                    url = new URL(caturl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    System.out.println(e.toString());

                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                } catch (IOException e) {
                    System.out.println(e.toString());
                } finally {
                    urlConnection.disconnect();
                }

                Emen_Contact emenObj = new Emen_Contact();
                ArrayList<Emen_Contact> arrEmen = emenObj.retireveAll(getApplicationContext());

                //Loop Thru Emergency Contacts & Send SOS
                for (int i = 0; i < arrEmen.size(); i++) {
                    System.out.println(""+arrEmen.get(i).getNumber());
                    try{
                        sendSMS(arrEmen.get(i).getNumber(), "SOS! Location: https://maps.google.com/?ll=" + lat + "," + lon + "&q=" + lat + "," + lon);
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                }
            }
        }


    };

    public void sendSMS(String mobileNo, String txtMessage) {
        try {
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(mobileNo, null, txtMessage, null, null);
            System.out.println("SMS Sent Successfully");
        } catch (Exception e) {
            System.out.println("SMS Failed to Send, Please try again");
            System.out.println(e.toString());
        }
    }

    public void sendData(String data) {

        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID); //showMessage("mBluetoothGatt null"+ mBluetoothGatt);
        if (RxService == null) {
            System.out.println("Rx service not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
        BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(RX_CHAR_UUID);
        if (RxChar == null) {
            System.out.println("Rx characteristic not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }

        RxChar.setValue(data.getBytes(Charset.forName("UTF-8")));
        boolean status = mBluetoothGatt.writeCharacteristic(RxChar);


        System.out.println("sending " + data);
    }

    public void enableTXNotification() {
    	/*
    	if (mBluetoothGatt == null) {
    		showMessage("mBluetoothGatt null" + mBluetoothGatt);
    		broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
    		return;
    	}
    		*/
        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
        if (RxService == null) {
            System.out.println("Rx service not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
        BluetoothGattCharacteristic TxChar = RxService.getCharacteristic(TX_CHAR_UUID);
        if (TxChar == null) {
            System.out.println("Tx charateristic not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(TxChar, true);

        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(CCCD);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);

    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);

    }


    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            peripheralTextView.append("Device Name: " + result.getDevice().getName() + " rssi: " + result.getRssi() + " Add: " + result.getDevice().getAddress() + "\n");
            System.out.println(result.getDevice().getName());
            System.out.println(deviceNameToConnect);
            if (result.getDevice().getName() != null) {
                System.out.println("NOT NULL");
                if (result.getDevice().getName().equals(deviceNameToConnect)) {
                    System.out.println("ATTEMPING TO CONNECT GATT");
                    gattConnect(result.getDevice());
                    System.out.println("GATT COMPLETE");
                    stopScanning();
                }
            }

            // auto scroll for text view
            final int scrollAmount = peripheralTextView.getLayout().getLineTop(peripheralTextView.getLineCount()) - peripheralTextView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                peripheralTextView.scrollTo(0, scrollAmount);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
    }

    public void startScanning() {
        System.out.println("start scanning");
        peripheralTextView.setText("");
        startScanningButton.setVisibility(View.INVISIBLE);
        stopScanningButton.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(leScanCallback);
            }
        });
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
        peripheralTextView.append("Stopped Scanning");
        startScanningButton.setVisibility(View.VISIBLE);
        stopScanningButton.setVisibility(View.INVISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });

    }


}
