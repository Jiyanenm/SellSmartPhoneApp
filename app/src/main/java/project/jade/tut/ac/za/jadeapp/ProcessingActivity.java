package project.jade.tut.ac.za.jadeapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import project.jade.tut.ac.za.jadeapp.util.AppConfig;
import project.jade.tut.ac.za.jadeapp.util.AppController;

public class ProcessingActivity extends AppCompatActivity {



    private static final String TAG = ProcessingActivity.class.getSimpleName();

    private EditText editName;
    private EditText editSurname;
    private EditText editEmail;
    private EditText editHouseNo;
    private EditText editStreetName;
    private EditText editSuburb;
    private EditText editCityName;
    private Button btnClear;
    private Button btnBuy;
    private ProgressDialog pDialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editHouseNo = (EditText) findViewById(R.id.editHouseNo);
        editStreetName = (EditText) findViewById(R.id.editStreetName);
        editSuburb = (EditText) findViewById(R.id.editSuburb);
        editCityName = (EditText) findViewById(R.id.editCity);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        btnClear = (Button) findViewById(R.id.btnClear);

        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setCancelable(false);
        pDialog.hide();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




               String name = editName.getText().toString().trim();
                String surname = editSurname.getText().toString().trim();
               String email =  editEmail.getText().toString().trim();
                String stName = editStreetName.getText().toString().trim();
                String houseNo = editHouseNo.getText().toString();
               String suburb =  editSuburb.getText().toString();
                String cityName =editCityName.getText().toString();



                //Check for empty data in the form
                if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty()&& !stName.isEmpty()&& !houseNo.isEmpty()&& !suburb.isEmpty()&& !cityName.isEmpty())
                {

                    System.exit(0);
                   // checkBuyProcessing(name,surname,email,stName,houseNo,suburb,cityName,phoneType);
                }
                else
                {
                    //prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please Complete The Form!", Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    public void checkBuyProcessing(final String name, final String surname, final String email, final String streetName, final String houseNo, final String suburb, final String city, final String phoneType)
    {
        //used to cancel the request
        final String tag_string_process = "buy_process";

        pDialog.setMessage("Process Buying...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_BUY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d(TAG, "Processing Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");

                    if (!name.equals(null) || !surname.equals(null) || !email.equals(null) || !streetName.equals(null) || !houseNo.equals(null) || !suburb.equals(null) || !city.equals(null)) {

                        Intent intentPrice = getIntent();
                        final String Price = intentPrice.getStringExtra("phonePrice");

                        Intent intentPhoneType = getIntent();
                        final String phoneType = intentPhoneType.getStringExtra("phoneType");

                        Intent intent = new Intent(getApplicationContext(), ProcessingActivity.class);

                        intent.putExtra("name", name);
                        intent.putExtra("surname", surname);
                        intent.putExtra("email", email);
                        intent.putExtra("streetName", streetName);
                        intent.putExtra("houseNo", houseNo);
                        intent.putExtra("suburb", suburb);
                        intent.putExtra("city", city);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter empty fields", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception error) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "Processing Buy Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }



        }){
            @Override
            protected Map<String,String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("surname", surname);
                params.put("email", email);
                params.put("streetName", streetName);
                params.put("houseNo", houseNo);
                params.put("suburb", suburb);
                params.put("city", city);

                return params;
            }


        };

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(strReq);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),
                    "No Settings Yet!...", Toast.LENGTH_LONG).show();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {

            System.exit(1);
            Toast.makeText(getApplicationContext(),
                    "GoodBye!...", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
