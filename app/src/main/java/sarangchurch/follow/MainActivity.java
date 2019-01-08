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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAT, buttonIfCody, buttonAddPraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddPraylist=(Button)findViewById(R.id.btn_addpraylist);
        buttonAddPraylist.setOnClickListener(this);
        buttonAT=(Button)findViewById(R.id.atcheck);
        buttonAT.setOnClickListener(this);
        buttonIfCody=(Button)findViewById(R.id.btn_if_cody);
        String leader = "리더";
        if(!leader.equals(MakeUserInfo.getJob())){
            buttonIfCody.setVisibility(View.VISIBLE);
            Log.e("E","없어");
//            Log.e("E",MakeUserInfo.getJob());
        }
        else{
            buttonIfCody.setVisibility(View.GONE);
            Log.e("E","있어");
        }
        buttonIfCody.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v==buttonAT){
            Intent intent = new Intent(getApplicationContext(),AttendanceCheck.class);
            startActivity(intent);
        }
        if(v==buttonIfCody){
            Log.e("e","눌림");
        }
        if(v==buttonAddPraylist){
            Intent intent = new Intent(getApplicationContext(),Praylist.class);
            startActivity(intent);
        }
    }





}