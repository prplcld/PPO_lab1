package com.example.lab1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    StringBuilder idStringBuilder = new StringBuilder("\nID ");
                    idStringBuilder.append(telephonyManager.getDeviceId());

                    TextView textView = findViewById(R.id.idView);
                    textView.setText(idStringBuilder.toString());
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("About Permission")
                            .setMessage("This permission is required to show device id. Would you like to grant permission again?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alert.show();
                }
        }
    }
}
