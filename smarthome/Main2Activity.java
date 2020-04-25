package com.vishnu.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.util.UUID;


public class Main2Activity extends AppCompatActivity {

    Button light;
    Button fan;
    String lightcontrol;
    String fancontrol;
    String fullcontrol;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        //address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device
        address = "24:6F:28:02:BA:B6";
        setContentView(R.layout.activity_main2);

        fan = (Button)findViewById(R.id.fan);
        light = (Button)findViewById(R.id.light);
        light.setBackgroundResource(R.drawable.lightoff);
        fan.setBackgroundResource(R.drawable.fanoff);
        lightcontrol = "lightoff";
        fancontrol = "fanoff";

        new ConnectBT().execute();

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lightcontrol.equals("lightoff"))
                {
                    lightcontrol = "lighton";
                    light.setBackgroundResource(R.drawable.lighton);
                }
                else if (lightcontrol.equals("lighton"))
                {
                    lightcontrol = "lightoff";
                    light.setBackgroundResource(R.drawable.lightoff);
                }
                fullcontrol = lightcontrol;
                finfun();
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fancontrol.equals("fanoff"))
                {
                    fancontrol = "fanon";
                    fan.setBackgroundResource(R.drawable.fanon);
                }
                else if (fancontrol.equals("fanon"))
                {
                    fancontrol = "fanoff";
                    fan.setBackgroundResource(R.drawable.fanoff);
                }
                fullcontrol = fancontrol;
                finfun();
            }
        });

    }

    private void finfun()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(fullcontrol.getBytes());
            }
            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Main2Activity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(getApplicationContext(),"Connection Failed. Is it a SPP Bluetooth? Try again.",Toast.LENGTH_LONG).show();

                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Connected.",Toast.LENGTH_LONG).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
