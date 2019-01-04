package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

public class Listitem extends AppCompatActivity {

    static boolean bool;
    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        listView = (ListView) findViewById(R.id.lv_items);

        getItems();

    }


    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz4MI6q8FQh5VUpE6MgbmXUFFZ1gRzXE07SOBDwqyAE0OtEUqC_/exec?action=getItems",
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

                String name = jo.getString("name");
                String id = jo.getString("id");
                String job = jo.getString("job");


                HashMap<String, String> item = new HashMap<>();
                item.put("name", name);
                item.put("id", id);
                item.put("job",job);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"name","id","job"},new int[]{R.id.name_blank,R.id.id_blank,R.id.job_blank});


        listView.setAdapter(adapter);

        Log.e("list size",""+list.size());
        for(int i=0; i<list.size();i++) {
            Log.e("something",list.get(i).get("name")+list.get(i).get("job"));
        }
        loading.dismiss();
    }
    public void CompareName(final String cpname, Context context){




        loading =  ProgressDialog.show(context,"Loading","please wait",false,true);
        Log.e("Err","3");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz4MI6q8FQh5VUpE6MgbmXUFFZ1gRzXE07SOBDwqyAE0OtEUqC_/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Err","4");

                        ArrayList<HashMap<String, String>> list = new ArrayList<>();

                        try {
                            Log.e("Err","5");
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("items");


                            for (int i = 0; i < jarray.length(); i++) {

                                JSONObject jo = jarray.getJSONObject(i);

                                String name = jo.getString("name");
                                String id = jo.getString("id");
                                String job = jo.getString("job");


                                HashMap<String, String> item = new HashMap<>();
                                item.put("name", name);
                                item.put("id", id);
                                item.put("job",job);

                                list.add(item);
                                Log.e("Err","6");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Err","1");
                        }


                        for(int i=0; i<list.size();i++) {
                            if(cpname.equals(list.get(i).get("name"))){
                                Listitem.bool =true;
                                Log.e("www",cpname+"   ===   "+list.get(i).get("name")+"  "+Listitem.bool);
                                break;
                            }
                            Log.e("www",cpname+"   ===   "+list.get(i).get("name")+"  "+Listitem.bool);

                        }

                        loading.dismiss();


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Err","2");
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        Log.e(" dsaf"," "+Listitem.bool);


    }


}