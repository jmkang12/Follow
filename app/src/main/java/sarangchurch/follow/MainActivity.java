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

    Button buttonAddItem,buttonReadItems,buttonFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonReadItems = (Button)findViewById(R.id.btn_read_item);
        buttonFile = (Button)findViewById(R.id.btn_file);
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

    }





}