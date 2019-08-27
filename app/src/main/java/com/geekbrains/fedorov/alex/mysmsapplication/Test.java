package com.geekbrains.fedorov.alex.mysmsapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Test extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String answer = intent.getStringExtra("hello");
        Toast.makeText(context, answer, Toast.LENGTH_SHORT).show();
    }
}
