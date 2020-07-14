package com.example.inspirem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class resetpassword extends AppCompatActivity {
    EditText pass,confirmpass;
    TextView email;
    Button btnchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email= (TextView) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.pass);
        confirmpass= (EditText) findViewById(R.id.confirmpass);

        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("email", "");
        email.setText(m);
        btnchange= (Button) findViewById(R.id.btnchange);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=email.getText().toString();
                String txtPassword=pass.getText().toString();
                String txtConfirmpassword=confirmpass.getText().toString();
                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword) || TextUtils.isEmpty(txtConfirmpassword)){
                    Toast.makeText(resetpassword.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else
                    registerNewAccoutn(txtEmail,txtPassword,txtConfirmpassword);


            }
        });

    }
    private void registerNewAccoutn(final String email, final String pass, final String confirmpass){
        final ProgressDialog progressDialog = new ProgressDialog(resetpassword.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Changing Passwords");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String uRl = Constants.URL_RESETPASSWORD;
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Sucessfully Changed")){
                    progressDialog.dismiss();
                    Toast.makeText(resetpassword.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(resetpassword.this,home.class));
                    finish();





                }else {
                    progressDialog.dismiss();
                    Toast.makeText(resetpassword.this,response,Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(resetpassword.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("pass",pass);
                param.put("confirmpass",confirmpass);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(resetpassword.this).addToRequestQueue(request);

    }




}

