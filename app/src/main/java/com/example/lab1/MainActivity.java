package com.example.lab1;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView versionName = findViewById(R.id.ver_name);
        versionName.setText(getVersion());
    }

    public String getVersion(){
        PackageInfo packageInfo;
        StringBuilder version = new StringBuilder("ver ");

        try{
            packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0);
            version.append(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version.toString();
    }
}
