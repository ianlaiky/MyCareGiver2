package com.example.mycaregiver2;

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
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Bluetooth {

    private String deviceNameToConnect;
    private static BluetoothGatt mBluetoothGatt;
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

    public Bluetooth() {
    }

    public static void setmBluetoothGatt(BluetoothGatt mBluetoothGatt) {
        Bluetooth.mBluetoothGatt = mBluetoothGatt;
    }

    public static void sendData(String data) {

        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID); //showMessage("mBluetoothGatt null"+ mBluetoothGatt);
        if (RxService == null) {
            System.out.println("Rx service not found!");

            return;
        }
        BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(RX_CHAR_UUID);
        if (RxChar == null) {
            System.out.println("Rx characteristic not found!");

            return;
        }

        RxChar.setValue(data.getBytes(Charset.forName("UTF-8")));
        boolean status = mBluetoothGatt.writeCharacteristic(RxChar);


        System.out.println("sending " + data);
    }



}
