package com.org.isdb62.service_brodcast_reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CUSTOM_BROADCAST_ACTION = "com.org.isdb62.service_broadcast_receiver.CUSTOM_ACTION";
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startServiceButton = findViewById(R.id.btn_start_service);
        Button stopServiceButton = findViewById(R.id.btn_stop_service);
        Button sendServiceButton = findViewById(R.id.btn_send_broadcast);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (CUSTOM_BROADCAST_ACTION.equals(intent.getAction())) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(context, "Broadcast Received" + message, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Custom Broadcast received: " + message);
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, new IntentFilter(CUSTOM_BROADCAST_ACTION));
    }
}