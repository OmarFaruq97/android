package com.org.isdb62.service_brodcast_reciever.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private Handler handler;
    private Runnable runnable;
    private int counter = 0;

    public BackgroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service Created");
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        //
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                counter++;
                Log.d(TAG, "Service Running... Counter: " + counter);
                Toast.makeText(BackgroundService.this,
                        "Service runnig: " + counter, Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 5000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service Start Command");
        Toast.makeText(this, "Service Start Command", Toast.LENGTH_SHORT).show();

        handler.post(runnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service Destroyed");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}