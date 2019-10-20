package com.example.lab1;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTelId();
        getVersion();
    }

    private void getVersion() {
        PackageInfo packageInfo;
        StringBuilder versionStringBuilder = new StringBuilder();
        versionStringBuilder.append("Version ");

        try {
            packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0);
            versionStringBuilder.append(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView textView = findViewById(R.id.versionView);
        textView.setText(versionStringBuilder.toString());
    }

    private void getTelId() {
        telephonyManager = (TelephonyManager) getSystemService(MainActivity.TELEPHONY_SERVICE);
        if (checkSelfPermission((Manifest.permission.READ_PHONE_STATE)) == PackageManager.PERMISSION_GRANTED) {

            StringBuilder idStringBuilder = new StringBuilder("ID ");
            idStringBuilder.append(telephonyManager.getDeviceId());

            TextView textView = findViewById(R.id.idView);
            textView.setText(idStringBuilder.toString());

        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "Permission is needed.", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && checkSelfPermission((Manifest.permission.READ_PHONE_STATE)) == PackageManager.PERMISSION_GRANTED){

                StringBuilder idStringBuilder = new StringBuilder("\nID ");
                idStringBuilder.append(telephonyManager.getDeviceId());

                TextView textView = findViewById(R.id.idView);
                textView.setText(idStringBuilder.toString());

            } else {
                Toast.makeText(this, "You should grant the permission to get id", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
