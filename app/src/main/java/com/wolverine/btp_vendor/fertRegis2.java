package com.wolverine.btp_vendor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import app.AppController;

public class fertRegis2 extends AppCompatActivity {
    Spinner Type;
    EditText Mobile,Village,Address1,Address2,LicenseNumber,IssueDate,ExpiryDate,WebSite;
    private String Dstate,DDistrict,DType,DMobile,DVillage,DAdress1,DAddress2,DLicense,DIssueDate,DExpiryDate,DWeb,name,state,district;
    Button register;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fermar_regis2);

        Intent i = getIntent();
        Bundle data = i.getExtras();
        name = data.getString("name");
        state = data.getString("state");
        district= data.getString("district");


        Type = (Spinner)findViewById(R.id.dealer_Type);
        Mobile = (EditText)findViewById(R.id.dealer_Mobile);
        Village= (EditText)findViewById(R.id.dealer_Village);
        Address1 = (EditText)findViewById(R.id.dealer_addressline1);
        Address2= (EditText)findViewById(R.id.dealer_addressline2);
        LicenseNumber= (EditText)findViewById(R.id.dealer_License);
        IssueDate= (EditText)findViewById(R.id.license_issue_date);
        ExpiryDate= (EditText)findViewById(R.id.license_expiry_date);
        WebSite= (EditText)findViewById(R.id.dealer_Web);
        register = (Button)findViewById(R.id.registerFert);

        DType = Type.getSelectedItem().toString();
        DMobile = Mobile.getText().toString();
        DVillage = Village.getText().toString();
        DAdress1 = Address1.getText().toString();
        DAddress2 = Address2.getText().toString();
        DLicense = LicenseNumber.getText().toString();
        DIssueDate = IssueDate.getText().toString();
        DExpiryDate = ExpiryDate.getText().toString();
        DWeb= WebSite.getText().toString();
    }

    private void fertRegisClass() throws UnsupportedEncodingException {
        pDialog = new ProgressDialog(fertRegis2.this);
        pDialog.setMessage("Registering ...");
        showDialog();
        final String nameEnco,stateEnco,districtEnco,village,add1,add2,license,issue,expiry,web;
        nameEnco = URLEncoder.encode(name, "utf-8");
        stateEnco= URLEncoder.encode(state, "utf-8");
        districtEnco = URLEncoder.encode(district, "utf-8");
        village= URLEncoder.encode(Village.getText().toString(), "utf-8");
        add1 = URLEncoder.encode(Address1.getText().toString(),"utf-8");
        add2= URLEncoder.encode(Address2.getText().toString(), "utf-8");
        license= URLEncoder.encode(LicenseNumber.getText().toString(), "utf-8");
        issue= URLEncoder.encode(IssueDate.getText().toString(), "utf-8");
        expiry= URLEncoder.encode(ExpiryDate.getText().toString(), "utf-8");
        web= URLEncoder.encode(WebSite.getText().toString(), "utf-8");

        StringRequest SpecialReq = new StringRequest(Request.Method.POST,"http://192.168.43.17/dealer_portal/regisfert.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        //hidePDialog();

                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(fertRegis2.this, MainActivity.class);
                        startActivity(i);
                        finish();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        hideDialog();
                        Toast.makeText(getApplicationContext(),"failure", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name", nameEnco);
                params.put("State", stateEnco);
                params.put("District", districtEnco);
                params.put("Type", Type.getSelectedItem().toString());
                params.put("Mobile", Mobile.getText().toString());
                params.put("Village", village);
                params.put("Add1", add1);
                params.put("Add2", add2);
                params.put("License", license);
                params.put("IssueDate", issue);
                params.put("ExpiryDate",expiry);
                params.put("Web",web);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(SpecialReq);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fermar_regis2, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fertVendRegis(View view) throws UnsupportedEncodingException {
        fertRegisClass();
    }
}
