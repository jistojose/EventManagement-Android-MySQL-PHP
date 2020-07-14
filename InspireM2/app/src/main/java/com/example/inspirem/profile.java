package com.example.inspirem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/* created by jisto jose on 19-03-2020 */
public class profile extends AppCompatActivity implements View.OnClickListener {


    public static final String UPLOAD_URL = Constants.URL_UPLOADPROFILEPICTURE;
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private ImageView buttonChoose;
    private ImageView buttonUpload;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;


    static EditText username,email,phone;
   // EditText imageName;
    Button btnedit,btnsave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username=(EditText) findViewById(R.id.tv);
        email=(EditText) findViewById(R.id.tv2);
        phone=(EditText) findViewById(R.id.tv3);

        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("email", "");
        String n = bb.getString("username", "");
        String v = bb.getString("phone", "");

       // String a = bb.getString("editedusername", "");
       // String b = bb.getString("editedphone", "");
       // Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, n, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, v, Toast.LENGTH_SHORT).show();

        email.setText(m);
        username.setText(n);
        phone.setText(v);
       // username.setText(a);
      //  phone.setText(b);

        buttonChoose = (ImageView) findViewById(R.id.imgedit);
        buttonUpload = (ImageView) findViewById(R.id.imgupload);
        btnedit=(Button)findViewById(R.id.btnedit);
        btnsave=(Button)findViewById(R.id.btnsave);
        //buttonView = (Button) findViewById(R.id.buttonViewImage);

        imageView = (ImageView) findViewById(R.id.profile_image);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        btnedit.setOnClickListener(this);






        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    //initialize and assign values
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
    //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.search:
                    startActivity(new Intent(getApplicationContext(),search.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.dashboard:
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(),notification.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profile:
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),home.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    });

}


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
        if(v == btnedit){
            editprofile();
        }
    }

    private void editprofile() {

        username.setCursorVisible(true);
        username.setFocusableInTouchMode(true);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.requestFocus(); //to trigger the soft input

        phone.setCursorVisible(true);
        phone.setFocusableInTouchMode(true);
        phone.setInputType(InputType.TYPE_CLASS_TEXT);
        phone.requestFocus(); //to trigger the soft input



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPhone=phone.getText().toString();
                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtPhone)){
                    Toast.makeText(profile.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else
                    updateprofile(txtUsername,txtEmail,txtPhone);


            }
        });


    }

    private void updateprofile(final String username, final String email, final String phone) {

        final ProgressDialog progressDialog = new ProgressDialog(profile.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setTitle("Changing User Data");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            String uRl = Constants.URL_UPDATEUSERPROFILE;
            StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("Sucessfully Changed")){
                        progressDialog.dismiss();
                        Toast.makeText(profile.this,response,Toast.LENGTH_SHORT).show();
                        finish();



                        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putString("username",username);
                        edit.putString("phone",phone);
                        edit.commit();





                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(profile.this,response,Toast.LENGTH_SHORT).show();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(profile.this,error.toString(),Toast.LENGTH_SHORT).show();

                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> param = new HashMap<>();
                    param.put("email",email);
                    param.put("username",username);
                    param.put("phone",phone);
                    return param;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(profile.this).addToRequestQueue(request);

        }





    private void uploadImage() {


            class UploadImage extends AsyncTask<Bitmap,Void,String> {

                ProgressDialog loading;
                RequestHandler rh = new RequestHandler();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(profile.this, "Uploading Image", "Please wait...",true,true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Bitmap... params) {
                    Bitmap bitmap = params[0];
                    String uploadImage = getStringImage(bitmap);

                    HashMap<String,String> data = new HashMap<>();
                    data.put(UPLOAD_KEY, uploadImage);

                    String result = rh.sendPostRequest(UPLOAD_URL,data);

                    return result;
                }
            }

            UploadImage ui = new UploadImage();
            ui.execute(bitmap);
        }


    private void showFileChooser() {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;

    }
}
