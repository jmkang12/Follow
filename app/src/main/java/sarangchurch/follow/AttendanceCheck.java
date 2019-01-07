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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    Log.e("TAG???", "checkedPositions: " + checkedItems.get(0));
                    if (checkedItems != null) {
                        Log.i("TAG???", "checkedPositions: " + checkedItems.size());
                        for (int i = 0; i < cntChoice; i++) {
                            if (checkedItems.get(i)) {
                                String item = listView.getAdapter().getItem(
                                        checkedItems.keyAt(i)).toString();
                                Log.e("TAG", item + " was selected");
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
