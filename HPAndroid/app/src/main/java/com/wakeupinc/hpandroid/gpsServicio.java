package com.wakeupinc.hpandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class gpsServicio extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//public class gpsServicio extends Service implements LocationListener {
//    //MediaPlayer reproductor;
//    private NotificationManager nm;
//    private static final int ID_NOTIFICACION_CREAR = 1;
//    public Handler handler = null;
//    public static Runnable runnable = null;
//    private LocationManager manejador;
//    private String proveedor;
//
//    @Override
//    public void onCreate() {
//
//        //reproductor = MediaPlayer.create(this, R.raw.audio);
//    }
//
//    @Override
//    public int onStartCommand(Intent intenc, int flags, int idArranque) {
////
////        Toast.makeText(this, "Servicio arrancado " + idArranque,
////                Toast.LENGTH_SHORT).show();
//
//
//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run() {
//                notificagps();
//                handler.postDelayed(runnable, 10000);
//            }
//        };
//
//        handler.postDelayed(runnable, 15000);
//
//
//        //reproductor.start();
//        return START_STICKY;
//    }
//    @Override
//    public void onDestroy() {
//        nm.cancel(ID_NOTIFICACION_CREAR);
////        Toast.makeText(this, "Servicio detenido",
////                Toast.LENGTH_SHORT).show();
//        //reproductor.stop();
//    }
//
//    @Override
//    public IBinder onBind(Intent intencion) {
//        return null;
//    }
//
//    // Métodos de la interfaz LocationListener
//    public void onLocationChanged(Location location) {
//        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criterio = new Criteria();
//        criterio.setAccuracy(Criteria.ACCURACY_FINE);
//        criterio.setCostAllowed(false);
//        criterio.setAltitudeRequired(false);
//
//        proveedor = manejador.getBestProvider(criterio, true);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        }
//        Location localizacion = manejador.getLastKnownLocation(proveedor);
////        Toast.makeText(this, "notificagps: "+localizacion.toString(),
////                Toast.LENGTH_SHORT).show();
//    }
//
//    public void onProviderDisabled(String proveedor) {
//    }
//
//    public void onProviderEnabled(String proveedor) {
//    }
//
//    public void onStatusChanged(String proveedor, int estado,Bundle extras) {
//    }
//    public void notificagps(){
//        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criterio = new Criteria();
//        criterio.setAccuracy(Criteria.ACCURACY_FINE);
//        criterio.setCostAllowed(false);
//        criterio.setAltitudeRequired(false);
//
//        proveedor = manejador.getBestProvider(criterio, true);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        }
//        Location localizacion = manejador.getLastKnownLocation(proveedor);
////        Toast.makeText(this, "notificagps: "+localizacion.toString(),
////                Toast.LENGTH_SHORT).show();
//    }


}