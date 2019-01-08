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

        listView = (ListView) findViewById(R.id.attend);

        getItems();
        buttonApply = (Button) findViewById(R.id.bt_checkapply);
        buttonApply.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonApply) {
                    Log.e("E", "my");
                    List<String> temp = null;
                    int cntChoice = listView.getCount();
                    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                    Log.e("TAG???", "checkedPositions: " + checkedItems.get(3));
                    if (checkedItems != null) {
                        Log.i("TAG???", "checkedPositions: " + checkedItems.size());
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
                                addItemToSheet(name,grade,date);
                                Log.e("TAG", name + " was selected"+grade);
                            }
                        }
                    } else {
                        Log.e("EERRR", "NULL");
//                Log.i("TAG???","checkedPositions: " + checkedItems.size());
                    }

                }
            }

        });

    }


    private void   addItemToSheet(String Name, String Grade, final String Date) {

        final String name=Name;
        final String grade=Grade;
        Log.e("E", "my2");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzXYrNIRWtdlho_7DjzlETwcEywXUabnrrHLGtM6fJrr6r0fyOr/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("E", "my3");
                        loading.dismiss();
                        Toast.makeText(AttendanceCheck.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("E", "my4");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","update");
                parmas.put("name",name);
                parmas.put("grade",grade);
                parmas.put("date",Date);

                Log.e("E", "my5");
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
        Log.e("E","www");
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
        Log.e("E","www2");
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            Log.e("E",jsonResposnce);
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            Log.e("E","www4");
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                Log.e("E","www5");
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
