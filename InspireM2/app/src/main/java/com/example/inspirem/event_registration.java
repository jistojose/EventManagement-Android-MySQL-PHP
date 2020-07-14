package com.example.inspirem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class event_registration extends AppCompatActivity {
    EditText eventname,eventprice,email,phone,address,state,postalcode,name;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);


        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        final String m = bb.getString("email", "");



        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String price = i.getStringExtra("price");
        eventname = findViewById(R.id.eventname);
        eventprice=findViewById(R.id.eventprice);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        state=findViewById(R.id.state);
        postalcode=findViewById(R.id.postalcode);
        name=findViewById(R.id.name);


        eventname.setText(title);
        eventname.setCursorVisible(false);
        eventname.setFocusableInTouchMode(false);
        eventname.setInputType(InputType.TYPE_CLASS_TEXT);
        eventname.requestFocus();

        eventprice.setText(price);
        eventprice.setCursorVisible(false);
        eventprice.setFocusableInTouchMode(false);
        eventprice.setInputType(InputType.TYPE_CLASS_TEXT);
        eventprice.requestFocus();

        email.setText(m);
        email.setCursorVisible(false);
        email.setFocusableInTouchMode(false);
        email.setInputType(InputType.TYPE_CLASS_TEXT);
        email.requestFocus();

        register=findViewById(R.id.btnpay);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEventname=eventname.getText().toString();
                String txtName=name.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPhone=phone.getText().toString();
                String txtAddress=address.getText().toString();
                String txtState=state.getText().toString();
                String txtPostalcode=postalcode.getText().toString();
                String txtPrice=eventprice.getText().toString();


                if(TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEventname) || TextUtils.isEmpty(txtPhone) || TextUtils.isEmpty(txtAddress) || TextUtils.isEmpty(txtState) || TextUtils.isEmpty(txtPostalcode) || TextUtils.isEmpty(txtPrice)){
                    Toast.makeText(event_registration.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else
                    Registerevents(txtEventname,txtName,txtEmail,txtPhone,txtAddress,txtState,txtPostalcode,txtPrice);


            }
        });


    }

    private void Registerevents(final String eventname, final String name, final String email, final String phone, final String address, final String state, final String postalcode, final String price ){
        final ProgressDialog progressDialog = new ProgressDialog(event_registration.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Storing Details ");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String uRl = Constants.URL_EVENTREGISTER ;
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Sucessfully Registered")){
                    progressDialog.dismiss();
                    Toast.makeText(event_registration.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(event_registration.this,payment.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(event_registration.this,response,Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(event_registration.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("eventname",eventname);
                param.put("name",name);
                param.put("email",email);
                param.put("phone",phone);
                param.put("address",address);
                param.put("state",state);
                param.put("postalcode",postalcode);
                param.put("price",price);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(event_registration.this).addToRequestQueue(request);

    }
}
