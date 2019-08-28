package com.geekbrains.fedorov.alex.mysmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br;
    IntentFilter intFilt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = findViewById(R.id.send);
        Button btnReceive = findViewById(R.id.receive);
        Button btnReg = findViewById(R.id.reg);
        Button btnUnReg = findViewById(R.id.unreg);

        /*br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String answer = intent.getStringExtra("hello");
                Toast.makeText(context, answer, Toast.LENGTH_SHORT).show();
            }
        };
        intFilt = new IntentFilter("com.geekbrains.fedorov.alex.mysmsapplication.TestedReceiver");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerReceiver(br, intFilt);
            }
        });

        btnUnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregisterReceiver(br);
            }
        });*/


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSend = new Intent("com.geekbrains.fedorov.alex.mysmsapplication.TestedReceiver");
//                 Тут прочитаем сообщение из бродкаста
                String prima = "test";//intent.getStringExtra("hello");
                intentSend.putExtra("hello", String.format("Привет %s от TestBroadcastReceiver", prima));
                intentSend.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                 И пошлем новый бродкаст
                getApplication().sendBroadcast(intentSend);
            }
        });
    }
}
