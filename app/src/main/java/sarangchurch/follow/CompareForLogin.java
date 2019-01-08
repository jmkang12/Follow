package sarangchurch.follow;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

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

public class CompareForLogin {
    ProgressDialog loading;
    static boolean bool=false;

    public void CompareName(final String cpname, final String birth, Context context){

        Log.e("ERR",MakeUserInfo.getName());


        loading =  ProgressDialog.show(context,"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz4MI6q8FQh5VUpE6MgbmXUFFZ1gRzXE07SOBDwqyAE0OtEUqC_/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<HashMap<String, String>> list = new ArrayList<>();

                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("items");


                            for (int i = 0; i < jarray.length(); i++) {

                                JSONObject jo = jarray.getJSONObject(i);

                                String name = jo.getString("name");
                                String id = jo.getString("birth");
                                String job = jo.getString("job");


                                HashMap<String, String> item = new HashMap<>();
                                item.put("name", name);
                                item.put("birth", id);
                                item.put("job",job);

                                list.add(item);

                            }
                        } catch (JSONException e) {
                            Log.e("ERR","ERRRRRR");
                            e.printStackTrace();
                        }


                        for(int i=0; i<list.size();i++) {
                            if(cpname.equals(list.get(i).get("name"))){
                                if(birth.equals(list.get(i).get("birth"))){
                                    MakeUserInfo.setJob(list.get(i).get("job"));
                                    CompareForLogin.bool =true;
                                    break;
                                }
                            }
                        }
                        loading.dismiss();
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

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);


    }

}
