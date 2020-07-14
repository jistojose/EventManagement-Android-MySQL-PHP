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

/* created by jisto jose on 15-01-2020 */
public class login extends AppCompatActivity {
     EditText email,pass;
    Button btnlogin;
    TextView signup;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        signup = (TextView) findViewById(R.id.txtsignup);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=email.getText().toString();
                String txtPassword=pass.getText().toString();

                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(login.this,"All Fields Required",Toast.LENGTH_SHORT).show();

                }
                else

                    login(txtEmail,txtPassword);

            }
        });
       // sharedPreferences = getSharedPreferences("UserInFo",MODE_PRIVATE);
       /* btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtEmail=email.getText().toString();
                String txtPassword=pass.getText().toString();
                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(login.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else

                    login(txtEmail,txtPassword);
                   // registerNewAccoutn(txtName,txtUsername,txtEmail,txtPhone,txtPassword,txtConfirmpassword);


            }
        });
*/
    }

    /*public void login(View view) {
        String txtEmail=email.getText().toString();
        String txtPassword=pass.getText().toString();
        if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
            Toast.makeText(login.this,"All Fields Required",Toast.LENGTH_SHORT).show();
        }
        else
            login(txtEmail,txtPassword);
    } */

    public void forgotpassword(View view) {

        Intent intent = new Intent(getApplicationContext(), forgotpassword.class);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(getApplicationContext(), signup.class);
        startActivity(intent);
    }

    private void login(final String email, final String pass) {
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Welcome User");
        progressDialog.setMessage("Please Wait");

        progressDialog.show();
        //String uRl = "http://192.168.0.101/inspiremApp/newlogin.php";
        String uRl = Constants.URL_LOGIN;


        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Login Success")){
                    progressDialog.dismiss();
                    Toast.makeText(login.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),home.class));
                    finish();



                    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("email",email);
                    edit.putString("password",pass);
                    edit.commit();

                 /*   Intent myIntent = new Intent(login.this, profile.class);
                    Bundle b =new Bundle();
                    b.putString("email",email);
                    b.putString("password",pass);
                    myIntent.putExtras(b); */

                   // myIntent.putExtra("email",email);

                   // startActivity(myIntent);
                   // startActivity(i);



                    //Intent intent = new Intent(getApplicationContext(), forgotpassword.class);
                   // startActivity(intent);
                    //finish();
                }
                else
                    progressDialog.dismiss();
                Toast.makeText(login.this,response,Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(login.this,home.class));
               // finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(login.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("pass", pass);
                return param;
            }


        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(login.this).addToRequestQueue(request);
    }


}