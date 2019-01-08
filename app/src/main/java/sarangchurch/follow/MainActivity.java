package sarangchurch.follow;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAT=(Button)findViewById(R.id.atcheck);
        buttonAT.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        if(v==buttonAT){
            Intent intent = new Intent(getApplicationContext(),AttendanceCheck.class);
            startActivity(intent);
        }

    }





}