package com.wolverine.btp_vendor;

import android.app.ProgressDialog;
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

public class pestRegis2 extends AppCompatActivity {
    Spinner Type;
    EditText mobile,village,address,website;
    Button regis;
    String name,state,district,Ename,Estate,Edistrict,Emobile,Evilage,Eaddress,Eweb;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mach_regis2);
        Intent i = getIntent();
        Bundle data = i.getExtras();
        name = data.getString("name");
        state = data.getString("state");
        district= data.getString("district");
        mobile = (EditText)findViewById(R.id.dealer_MobileMach);
        village = (EditText)findViewById(R.id.dealer_VillageMach);
        address = (EditText)findViewById(R.id.dealer_addresslineMach);
        website = (EditText)findViewById(R.id.dealer_WebMach);
        Type = (Spinner)findViewById(R.id.dealer_TypeMach);
        regis = (Button)findViewById(R.id.registerMach);
    }

    private void register() {
        pDialog = new ProgressDialog(pestRegis2.this);
        pDialog.setMessage("Logging in ...");
        showDialog();
        String url="http://192.168.43.17/dealer_portal/regispest.php?Name="+Ename+
                "&State="+Estate+"&District="+Edistrict+"&Type="+Type.getSelectedItem().toString()+
                "&Mobile="+Emobile+"&Village="+Evilage+"&Add="+Eaddress+"&Web="+Eweb;
        Log.d("url=============","http://192.168.43.17/dealer_portal/regispest.php?Name="+Ename+
                "&State="+Estate+"&District="+Edistrict+"&Type="+Type.getSelectedItem().toString()+
                "&Mobile="+Emobile+"&Village="+Evilage+"&Add="+Eaddress+"&Web="+Eweb);
        StringRequest SpecialReq = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        //hidePDialog();

                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(pestRegis2.this, MainActivity.class);
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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
        getMenuInflater().inflate(R.menu.menu_mach_regis2, menu);
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

    public void machvendregis(View view) {
        try {
            Ename = URLEncoder.encode(name,"utf-8");
            Estate= URLEncoder.encode(state,"utf-8");
            Edistrict= URLEncoder.encode(district,"utf-8");
            Emobile= URLEncoder.encode(mobile.getText().toString(),"utf-8");
            Evilage= URLEncoder.encode(village.getText().toString(),"utf-8");
            Eaddress= URLEncoder.encode(address.getText().toString(),"utf-8");
            Eweb= URLEncoder.encode(website.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("ValesMach",Ename+Estate+Edistrict+Emobile+Evilage+Eaddress+Eweb+Type.getSelectedItem().toString());
        register();
    }
}
