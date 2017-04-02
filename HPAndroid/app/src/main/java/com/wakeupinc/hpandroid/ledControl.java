package com.wakeupinc.hpandroid;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class ledControl extends ActionBarActivity {

    TextView bpm, estado;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String edo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recivimos la mac address obtenida en la actividad anterior
        bpm = (TextView) findViewById(R.id.lay2);
        estado = (TextView) findViewById(R.id.lay3);

        new ConnectBT().execute(); //Call the class to connect
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }
            catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut; }
        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;
            while (true) {
                try {
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    for(int i = begin; i < bytes; i++) {
                        if(buffer[i] == "E".getBytes()[0]) {
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            edo="Estable";
                            begin = i + 1;
                            if(i == bytes - 1) {
                                bytes = 0; begin = 0;
                            }
                        }
                        else{
                        if(buffer[i] == "L".getBytes()[0]) {
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            edo="Lento";
                            begin = i + 1;
                            if(i == bytes - 1) {
                                bytes = 0; begin = 0;
                            }
                        }
                            else{
                            if(buffer[i] == "A".getBytes()[0]) {
                                mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                                edo="Agitado";
                                begin = i + 1;
                                if(i == bytes - 1) {
                                    bytes = 0; begin = 0;
                                }
                            }
                        }
                    }
                    }
                }
                catch (IOException e) {
                    break;
                }
            }
        }
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }
        public void cancel() {
            try {
                mmSocket.close(); }
    catch (IOException e) { }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int)msg.arg1;
            int end = (int)msg.arg2;
            switch(msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    //Toast.makeText(getApplicationContext(), writeMessage, Toast.LENGTH_LONG).show();
                    bpm.setText(writeMessage);
                    estado.setText(edo);
                    break;
            }
        }
    };

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish();

    }

    /*
        private void turnOffLed()
        {
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write("TF".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        }

        private void turnOnLed()
        {
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write("TO".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        }

    */
    public void envia() {
        InputStream is = null;
        byte[] buffer = new byte[1024];
        int begin = 0, bytes = 0;
        try {
            is = btSocket.getInputStream();
            bytes += is.read(buffer, bytes, buffer.length - bytes);
            for(int i=begin;i<bytes;i++){
                if(buffer[i]=="m".getBytes()[0]){

                }
            }
            DataInputStream dinput = new DataInputStream(is);
            bpm.setText(dinput.readLine());
            //dinput.readFully(buffer, 0, buf fer.length));

        } catch (IOException e) {
            msg("Error");
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Conexión Fallida");
                finish();
            } else {
                msg("Conectado");
                ConnectedThread sap = new ConnectedThread(btSocket);
                sap.start();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
