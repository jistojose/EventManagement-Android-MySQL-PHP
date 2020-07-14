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

public class deleteaccount extends AppCompatActivity {
    TextView email;
    Button btndelete,btncancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteaccount);

        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email= (TextView) findViewById(R.id.email);
        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("email", "");
        email.setText(m);
        btncancel=(Button)findViewById(R.id.btncancel);
        btndelete= (Button) findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=email.getText().toString();

                if(TextUtils.isEmpty(txtEmail)){
                    Toast.makeText(deleteaccount.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else
                    deleteAccoutn(txtEmail);


            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(deleteaccount.this,settings.class));
                finish();

            }
        });

    }
    private void deleteAccoutn(final String email){
        final ProgressDialog progressDialog = new ProgressDialog(deleteaccount.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Deleting Account");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String uRl = Constants.URL_DELETE_USER;
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Sucessfully Deleted")){
                    progressDialog.dismiss();
                    Toast.makeText(deleteaccount.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(deleteaccount.this,login.class));
                    finish();



                }else {
                    progressDialog.dismiss();
                    Toast.makeText(deleteaccount.this,response,Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(deleteaccount.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email",email);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(deleteaccount.this).addToRequestQueue(request);

    }




}

