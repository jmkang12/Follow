package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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

public class AddItemToSheet {
    AddItemToSheet(EditText editTextName, EditText editTextId, EditText editTextAge, EditText editTextJob, Context context){
        this.editTextName=editTextName;
        this.editTextAge=editTextAge;
        this.editTextId=editTextId;
        this.editTextJob=editTextJob;
        this.context=context;
    }
    EditText editTextName,editTextId,editTextAge,editTextJob;
    Context context;

    public void  addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(context,"Adding Item","Please wait");
        final String name = editTextName.getText().toString().trim();
        final String id = editTextId.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String job = editTextJob.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz4MI6q8FQh5VUpE6MgbmXUFFZ1gRzXE07SOBDwqyAE0OtEUqC_/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        loading.dismiss();
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context.getApplicationContext(),MainActivity.class);
                        context.startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("name",name);
                parmas.put("id",id);
                parmas.put("age",age);
                parmas.put("job",job);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(stringRequest);


    }
}
