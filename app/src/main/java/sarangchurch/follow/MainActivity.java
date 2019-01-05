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

    Button buttonAddItem,buttonReadItems,buttonFile,buttonAT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonReadItems = (Button)findViewById(R.id.btn_read_item);
        buttonFile = (Button)findViewById(R.id.btn_file);
        buttonAT=(Button)findViewById(R.id.atcheck);
        buttonAT.setOnClickListener(this);
        buttonAddItem.setOnClickListener(this);
        buttonReadItems.setOnClickListener(this);
        buttonFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){

            Intent intent = new Intent(getApplicationContext(),Additem.class);
            startActivity(intent);
        }
        if(v==buttonReadItems){

            Intent intent = new Intent(getApplicationContext(),Listitem.class);
            startActivity(intent);
        }
        if(v==buttonFile){
            Intent intent = new Intent(getApplicationContext(),EditUserInfo.class);
            startActivity(intent);
        }
        if(v==buttonAT){
            Intent intent = new Intent(getApplicationContext(),AttendanceCheck.class);
            startActivity(intent);
        }

    }





}