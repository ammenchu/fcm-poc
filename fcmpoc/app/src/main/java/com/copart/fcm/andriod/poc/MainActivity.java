package com.copart.fcm.andriod.poc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btn_ok;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(FcmConstants.TAG, "bundle :== " + savedInstanceState);

        if (getIntent() != null) {
            //String message_id = getIntent().getStringExtra("message_id");
            //Log.d(FcmConstants.TAG, "message_id :== " + message_id);
        }

        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder(FcmConstants.SENDER_ID + "@gcm.googleapis.com")
                .setMessageId(String.valueOf(random.nextLong()))
                .build());
    }
}
