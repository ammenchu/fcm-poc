package com.copart.fcm.andriod.poc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

                RemoteMessage message = new RemoteMessage.Builder(FcmConstants.SENDER_ID + "@gcm.googleapis.com")
                        .setMessageId(Integer.toString(random.nextInt(9999)))
                        .addData("success", "This is accepted acknowledgement from andriod app")
                        .build();

                if (!message.getData().isEmpty()) {
                    Log.e(FcmConstants.TAG, "UpstreamData: " + message.getData());
                }

                if (!message.getMessageId().isEmpty()) {
                    Log.e(FcmConstants.TAG, "UpstreamMessageId: " + message.getMessageId());
                }
                firebaseMessaging.send(message);
            }
        });
    }
}
