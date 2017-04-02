package com.wakeupinc.hpandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by vaioubuntu on 5/5/16.
 */
public class BluetoothServicio extends Service {

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {


        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
