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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.app.ProgressDialog.*;

/* created by jisto jose on 15-01-2020 */
public class signup extends AppCompatActivity {
    EditText name,username,email,phone,pass,confirmpass;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username= (EditText) findViewById(R.id.username);
        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.phone);
        pass= (EditText) findViewById(R.id.pass);
        confirmpass= (EditText) findViewById(R.id.confirmpass);
        btnsignup= (Button) findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName=name.getText().toString();
                String txtUsername=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPhone=phone.getText().toString();
                String txtPassword=pass.getText().toString();
                String txtConfirmpassword=confirmpass.getText().toString();
                if(TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPhone) || TextUtils.isEmpty(txtPassword) || TextUtils.isEmpty(txtConfirmpassword)){
                    Toast.makeText(signup.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
              else
                  registerNewAccoutn(txtName,txtUsername,txtEmail,txtPhone,txtPassword,txtConfirmpassword);


            }
        });

    }
    private void registerNewAccoutn(final String name, final String username, final String email, final String phone, final String pass, final String confirmpass){
        final ProgressDialog progressDialog = new ProgressDialog(signup.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering New Account");
        progressDialog.show();
        String uRl = Constants.URL_REGISTER; ;
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Sucessfully Registered")){
                    progressDialog.dismiss();
                    Toast.makeText(signup.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this,home.class));
                    finish();


                    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("email",email);
                    edit.putString("username",username);
                    edit.putString("phone",phone);
                    edit.commit();


                }else {
                    progressDialog.dismiss();
                    Toast.makeText(signup.this,response,Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(signup.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> param = new HashMap<>();
                    param.put("name",name);
                    param.put("username",username);
                    param.put("email",email);
                    param.put("phone",phone);
                    param.put("pass",pass);
                    param.put("confirmpass",confirmpass);
                    return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(signup.this).addToRequestQueue(request);

    }

}
