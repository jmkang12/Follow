package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttendanceCheck extends AppCompatActivity {

    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;
    Button buttonApply;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_check_list);
        final Context context = this;

        listView = (ListView) findViewById(R.id.attend);

        getItems();
        buttonApply = (Button) findViewById(R.id.bt_checkapply);
        buttonApply.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonApply) {
                    List<String> temp = null;
                    int cntChoice = listView.getCount();
                    Integer checkednumber=0;
                    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                    if (checkedItems != null) {
                        for (int i = 0; i < cntChoice; i++) {
                            if (checkedItems.get(i)) {
                                String item = listView.getAdapter().getItem(i).toString();
                                int n_start,n_end,g_start,g_end;
                                n_start=item.lastIndexOf('=');
                                n_end=item.indexOf('}');
                                g_start=item.indexOf('=');
                                g_end=item.indexOf(',');
                                String name,grade;
                                name=item.substring(n_start+1,n_end);
                                grade=item.substring(g_start+1,g_end);
                                String date = new SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault()).format(new Date());

                                date = date.substring(2,4)+"년"+date.substring(5,7)+date.substring(8,10);
                                Log.e("Date", date);
                                addItemToSheet(context,name,grade,date, true);
                                checkednumber++;
                            }
                        }
                    }
                    else {
                        for (int i = 0; i < cntChoice; i++) {
                            if (!checkedItems.get(i)) {
                                String item = listView.getAdapter().getItem(i).toString();
                                int n_start,n_end,g_start,g_end;
                                n_start=item.lastIndexOf('=');
                                n_end=item.indexOf('}');
                                g_start=item.indexOf('=');
                                g_end=item.indexOf(',');
                                String name,grade;
                                name=item.substring(n_start+1,n_end);
                                grade=item.substring(g_start+1,g_end);
                                String date = new SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault()).format(new Date());
                                date = date.substring(2,4)+"년"+date.substring(5,7)+date.substring(8,10);
                                Log.e("Date", date);
                                addItemToSheet(context,name,grade,date, false);
                                checkednumber++;
                            }
                        }
                    }
                    addAttendanceNumerToSheet(context,checkednumber,MakeUserInfo.getName());
                    Log.e("Number", ""+checkednumber);

                }
            }

        });

    }


    private void addAttendanceNumerToSheet(Context context, Integer checkednumber, String name){
        final String number = checkednumber.toString();
        final String leadername = name;

        loading =  ProgressDialog.show(context,"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxu_F1a5Ow5-331YHF_me6Cd2Cau18CV9fAtn-8Lt6Bpn0HWIkf/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AttendanceCheck.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

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
                parmas.put("action","add");
                parmas.put("leadername",leadername);
                parmas.put("number",number);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }



    private void   addItemToSheet(Context context,String Name, String Grade, String Date, boolean bool) {

        final String name=Name;
        final String grade=Grade;
        final String date=Date;
        final boolean b = bool;
        loading =  ProgressDialog.show(context,"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzXYrNIRWtdlho_7DjzlETwcEywXUabnrrHLGtM6fJrr6r0fyOr/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AttendanceCheck.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

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
                if(b) {
                    parmas.put("action", "update");
                }
                else{
                    parmas.put("action", "update2");
                }
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


    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzt7N8IyGFdkUePMkwpWyViPYL3F2i856KynC0oeC-dXlksGMQ/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {

            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String leadername = jo.getString("leadername");
                if(!leadername.equals(MakeUserInfo.getName())){
                    continue;
                }
                String name = jo.getString("name");
                String grade = jo.getString("grade");

                HashMap<String, String> item = new HashMap<>();
               // item.put("leadername", leadername);
                item.put("name", name);
                item.put("grade",grade+"학년");

                list.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERR","ERROR");
        }

        adapter = new SimpleAdapter(this,list,R.layout.attendance_check_list_row,
            new String[]{"name","grade"},new int[]{R.id.name_blankAC,R.id.grade_blankAC});
       // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        loading.dismiss();
    }
}
