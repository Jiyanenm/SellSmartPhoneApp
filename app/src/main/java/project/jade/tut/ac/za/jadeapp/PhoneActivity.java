package project.jade.tut.ac.za.jadeapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.jade.tut.ac.za.jadeapp.model.Phone;
import project.jade.tut.ac.za.jadeapp.util.AppConfig;
import project.jade.tut.ac.za.jadeapp.util.AppController;
import project.jade.tut.ac.za.jadeapp.util.ListPhoneAdapter;


public class PhoneActivity extends AppCompatActivity {

    //Log tag
    private static  final String TAG = PhoneActivity.class.getSimpleName();
    public ProgressDialog pDialog;
    private List<Phone> phoneList = new ArrayList<Phone>();
    private ListView listView;
    private EditText inputSearch;
    private ListPhoneAdapter adapter;
    Phone objPhone = new Phone();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        listView = (ListView) findViewById(R.id.list);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        pDialog = new ProgressDialog(this);

        pDialog.setMessage("Loading Phones....");
        pDialog.setTitle("Please Wait");
        pDialog.show();

        //Get values from Intent
        Intent intent = getIntent();
       final String phoneBrand = intent.getStringExtra("phoneType");
       final String phonePrice = intent.getStringExtra("phonePrice");

       // System.out.println("Phone Brand" + phoneBrand);
       // System.out.println("Phone Price" + phonePrice);
       pullAllPhoneData();
       // pullPhoneData();
       // Test();
        adapter = new ListPhoneAdapter(this ,phoneList);
        listView.setAdapter(adapter);



        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {


               // PhoneActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //pop up dialog for more information
        final AlertDialog.Builder popUpDialog = new AlertDialog.Builder(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //showTimelineData();

                popUpDialog.setTitle("More About " + objPhone.getBrand());
                popUpDialog.setMessage("RAM " + objPhone.getRam() + " ,Extras" + objPhone.getExtraFeature());
                popUpDialog.setIcon(android.R.drawable.btn_star_big_on);
                popUpDialog.setNegativeButton("Close", null);
                popUpDialog.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent1 = new Intent(getApplicationContext(),ProcessingActivity.class);

                        Bundle bundle = new Bundle();
                        String id = String.valueOf(objPhone.getId());
                        String brandB = objPhone.getBrand();
                        String modelM = objPhone.getModel();
                        String priceP = String.valueOf(objPhone.getPrice());
                        String extraFeatureF = objPhone.getExtraFeature();
                        String serialNumberM = objPhone.getSerialNumber();
                        String imageI = objPhone.getImage();

                        bundle.putString("brand",brandB);
                        bundle.putString("model",modelM);



                        startActivity(intent1);

                    }
                });
                popUpDialog.show();
            }
        });
        final AlertDialog.Builder popUpDialogBuy = new AlertDialog.Builder(this);



    }
    public void pullAllPhoneData()
    {
        JsonArrayRequest phonesReq = new JsonArrayRequest(AppConfig.URL_SHOWALL_PHONES, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, response.toString());
                hidePDialog();
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        objPhone.setId(obj.getInt("id"));
                        objPhone.setBrand(obj.getString("brand"));
                        objPhone.setModel(obj.getString("model"));
                        objPhone.setPrice(obj.getDouble("price"));
                        objPhone.setRam(obj.getString("ram"));
                        objPhone.setExtraFeature(obj.getString("extraFeature"));
                        objPhone.setSerialNumber((obj.getString("serialNumber")));
                        objPhone.setExtraFeature((obj.getString("extraFeature")));
                        objPhone.setImage((obj.getString("image")));
                        //Check for error node in json

                        phoneList.add(objPhone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                // notifying list adapter about data changes
                // so that it renders the list view with updated data

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(phonesReq);

    }
    public void pullPhoneData()
    {
        final JSONObject jsonobject_one = new JSONObject();
        try {


            Intent intent = getIntent();
            final String phoneBrand = intent.getStringExtra("phoneType");
            System.out.println("Phone Brand is " + phoneBrand);
            jsonobject_one.put("brand", phoneBrand);
            System.out.println("JSON ONE" + jsonobject_one);



        }catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,AppConfig.URL_SHOWALL_PHONES, jsonobject_one,
new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {



                        Log.d(TAG, response.toString());
                        System.out.println("JSOH PHONE >>>>response" + response);
                        //msgResponse.setText(response.toString());
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("JSOH error PHONE >>>>response" + error);
                listView.setAdapter(adapter);
                hidePDialog();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        System.out.println("JSON Request " + jsonObjReq);

        // Adding request to request queue
      AppController.getmInstance().addToRequestQueue(jsonObjReq);


    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Buy");
        menu.add(0, v.getId(), 0, "Back");
    }
    public boolean onContextItemSelected(MenuItem item){

        if(item.getTitle()=="Buy")
        {
            Intent intent = new Intent(getApplicationContext(),ProcessingActivity.class);
            startActivity(intent);
        }

        if(item.getTitle()=="Back")
        {
            Toast.makeText(getApplicationContext(), "Try Another Phone..", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void Test()
    {
        String tag_string_req = "reg";

        final JSONObject jsonobject_one = new JSONObject();
        try {


            Intent intent = getIntent();
            final String phoneBrand = intent.getStringExtra("phoneType");
            System.out.println("Phone Brand is " + phoneBrand);
            jsonobject_one.put("brand", phoneBrand);
            System.out.println("JSON ONE" + jsonobject_one);



        }catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SHOWALL_PHONES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "pHONE Response: " + response.toString());
                hidePDialog();

                try {
                    JSONObject jObj = new JSONObject(response);


                   // JSONObject phone = jObj.getJSONObject("phone");
                    //String name = phone.getString("brand");
                   // String price = phone.getString("price");

                   // System.out.println("Nammmmme " + name);
                   // System.out.println("Priceeeeeee " + price);
                    System.out.print("On Responseeeee " + response);

                    phoneList.add(objPhone);
                    adapter.notifyDataSetChanged();
                    // Launch main activity
                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(intent);
                    finish();


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        System.out.println("json object in error " + obj);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidePDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Intent intent = getIntent();
                final String phoneBrand = intent.getStringExtra("phoneType");
                System.out.println("Phone Brand is " + phoneBrand);
                Map<String, String> params = new HashMap<String, String>();
                params.put("brand", phoneBrand);


                return params;
            }

        };

        // Adding request to request queue
        System.out.println("before sending reqest is " + jsonobject_one);
        AppController.getmInstance().addToRequestQueue(strReq,jsonobject_one.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        hidePDialog();
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}





