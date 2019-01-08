package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Praylist extends AppCompatActivity {

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
                    SparseBooleanArray booleanArray = listView.getCheckedItemPositions();
                    int cntChoice = listView.getCount();

                    if (booleanArray != null) {
                        for (int i = 0; i < cntChoice; i++) {
                            if (booleanArray.get(i)) {
                                String item = listView.getAdapter().getItem(i).toString();
                                AddPray.myname=item;
                                Intent intent = new Intent(getApplicationContext(), AddPray.class);
                                startActivity(intent);


                            }
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "선택 해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
        ArrayList<String> stringList= new ArrayList<>();
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
                stringList.add(name);
                list.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERR","ERROR");
        }

        ArrayAdapter<String> stadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice, stringList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(stadapter);

        loading.dismiss();
    }
}
