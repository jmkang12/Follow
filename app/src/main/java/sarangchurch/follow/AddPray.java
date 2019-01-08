package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class AddPray extends AppCompatActivity {
    Button buttonAddPray;
    EditText Pray;
    static String myname;
    ProgressDialog loading;

    protected void onCreate(Bundle savedInstanceState) {
        final Context context =this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forpray);
        buttonAddPray=(Button)findViewById(R.id.addpray);
        Pray=(EditText)findViewById(R.id.et_pray);
        buttonAddPray.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prays = Pray.getText().toString();
                prays= prays.replace("\n"," ");
                Log.e("쓴거",prays);
                addItemToSheet(context,myname,prays,"pray");

            }
        });



    }

    private void   addItemToSheet(Context context, String Name, String Grade, String Date) {
        final String name=Name;
        final String grade=Grade;
        final String date=Date;
        loading =  ProgressDialog.show(context,"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzXYrNIRWtdlho_7DjzlETwcEywXUabnrrHLGtM6fJrr6r0fyOr/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddPray.this,response,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        //startActivity(intent);
                        AddPray.this.finish();

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

                parmas.put("action","update3");
                parmas.put("name",name);
                parmas.put("grade",grade);
                parmas.put("date",date);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }
}
