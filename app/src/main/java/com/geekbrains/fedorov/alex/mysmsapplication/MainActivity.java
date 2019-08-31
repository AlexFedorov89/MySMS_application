package com.geekbrains.fedorov.alex.mysmsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int permissionRequestCode = 778;
    private EditText txtMobile;
    private EditText txtMessage;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if ((grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) && (grantResults.length > 1 && (grantResults[1] == PackageManager.PERMISSION_GRANTED))) {
                Toast.makeText(getApplicationContext(), "Спасибо!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Извините, приложение без данных разрешений может работать неправильно",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        requestPermission();
    }

    private void initViews() {
        Button btnSend = findViewById(R.id.send);
        txtMobile = findViewById(R.id.txtMobile);
        txtMessage = findViewById(R.id.txtMessage);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    private void sendSMS() {
        String address = txtMobile.getText().toString();
        String sms_body = txtMessage.getText().toString();

        if (isNotNeedPermission() || isPermissionGranted()) {

            try {
                if (address.isEmpty()){
                    Toast.makeText(this, "The recipient number must be filled in", Toast.LENGTH_SHORT).show();

                    return;
                }
                SmsManager sm = SmsManager.getDefault();

                sm.sendTextMessage(address, null, sms_body, null, null);
                Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "SMS failed to send, please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ address));
            smsIntent.putExtra("sms_body", sms_body);
            startActivity(smsIntent);
        }
    }

    private void requestPermission() {
        if (!isPermissionGranted()) {
            final String[] permissions = new String[]{ Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS};
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        }
    }

    private boolean isNotNeedPermission(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    private boolean isPermissionGranted() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                ));
    }
}
