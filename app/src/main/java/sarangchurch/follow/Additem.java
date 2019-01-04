package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Additem extends AppCompatActivity implements View.OnClickListener {


    EditText editTextName,editTextId,editTextAge,editTextJob;
    Button buttonAddItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_item);

        editTextName = (EditText)findViewById(R.id.et_name);
        editTextId = (EditText)findViewById(R.id.et_id);
        editTextAge = (EditText)findViewById(R.id.et_age);
        editTextJob = (EditText)findViewById(R.id.et_job);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            //addItemToSheet();


            AddItemToSheet addItemToSheet = new AddItemToSheet(editTextName,editTextId,editTextAge,editTextJob,this);
            addItemToSheet.addItemToSheet();
            //Define what to do when button is clicked
        }



    }
}