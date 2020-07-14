package com.example.inspirem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* created by jisto jose on 24-04-2020 */
public class editevents extends AppCompatActivity {
    DatePickerDialog dp1;
    EditText editext1 , editext2 ,name ,ewhere ,type ,category ,mode ,photo,speaker,price,description;
    Button btnChoose,btncreate;
    ImageView imageview;
    private final int PICK_IMAGE_REQUEST = 100;
    private Uri filePath;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editevents);



        // to avoid actionbar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email= (TextView) findViewById(R.id.email);
        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("email", "");
        email.setText(m);


        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.eventcategory);
        final Spinner spinner2 = (Spinner) findViewById(R.id.eventmode);
        final Spinner spinner3 = (Spinner) findViewById(R.id.eventtype);
        btnChoose = (Button) findViewById(R.id.btnchoose);
        imageview = (ImageView) findViewById(R.id.imageView3);


        editext1=findViewById(R.id.eventstart);
        editext2=findViewById(R.id.eventends);
        editext1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c1=Calendar.getInstance();
                int day=c1.get(Calendar.DAY_OF_MONTH);
                int month=c1.get(Calendar.MONTH);
                int year=c1.get(Calendar.YEAR);
                dp1=new DatePickerDialog(editevents.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String currentDateStrig = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

                        EditText Editext1 = (EditText) findViewById(R.id.eventstart);
                        Editext1.setText(currentDateStrig);
                    }
                },year,month,day);
                dp1.show();
            }
        });
        editext2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c1=Calendar.getInstance();
                int day=c1.get(Calendar.DAY_OF_MONTH);
                int month=c1.get(Calendar.MONTH);
                int year=c1.get(Calendar.YEAR);
                dp1=new DatePickerDialog(editevents.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String currentDateStrig = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

                        EditText Editext2 = (EditText) findViewById(R.id.eventends);
                        Editext2.setText(currentDateStrig);
                    }
                },year,month,day);
                dp1.show();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view();
            }
        });

        name= (EditText) findViewById(R.id.eventname);
        ewhere= (EditText) findViewById(R.id.eventwhere);
        speaker=(EditText) findViewById(R.id.eventspeaker);
        price=(EditText) findViewById(R.id.eventprice);
        description=(EditText) findViewById(R.id.eventDescription);


        SharedPreferences bc = getSharedPreferences("my_prefs2", 0);
        String eventname = bc.getString("name", "");
        String eventwhere = bc.getString("ewhere", "");
        String eventspeaker = bc.getString("speaker", "");
        String eventprice = bc.getString("price", "");
        String eventdescription = bc.getString("description", "");
        String eventstart = bc.getString("eventstart", "");
        String eventends = bc.getString("eventends", "");

        // String a = bb.getString("editedusername", "");
        // String b = bb.getString("editedphone", "");
        // Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, n, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, v, Toast.LENGTH_SHORT).show();

        name.setText(eventname);
        ewhere.setText(eventwhere);
        speaker.setText(eventspeaker);
        price.setText(eventprice);
        description.setText(eventdescription);
        editext1.setText(eventstart);
        editext2.setText(eventends);



        btncreate= (Button) findViewById(R.id.btncreate);
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName=name.getText().toString();
                String txtEventstart=editext1.getText().toString();
                String txtEventends=editext2.getText().toString();
                String txtEwhere=ewhere.getText().toString();
                String txtSpeaker=speaker.getText().toString();
                String txtPrice=price.getText().toString();
                String txtDescription=description.getText().toString();

                String txtType=spinner3.getSelectedItem().toString();
                String txtCategory=spinner.getSelectedItem().toString();
                String txtMode=spinner2.getSelectedItem().toString();
                String txtPhoto=imageview.toString();
                String txtEmail=email.getText().toString();
                if(TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEventstart) || TextUtils.isEmpty(txtEventends) || TextUtils.isEmpty(txtEwhere) || TextUtils.isEmpty(txtType) || TextUtils.isEmpty(txtCategory) || TextUtils.isEmpty(txtMode) || TextUtils.isEmpty(txtDescription)){
                    Toast.makeText(editevents.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else
                    createNewevents(txtName,txtEventstart,txtEventends,txtEwhere,txtType,txtCategory,txtSpeaker,txtPrice,txtMode,txtPhoto,txtDescription,txtEmail);


            }
        });

        // Spinner click listener

        // Spinner for event category
        List<String> categories = new ArrayList<String>();
        categories.add("< Select Your Category >");
        categories.add("Arts and Culture");
        categories.add("Children and Family");
        categories.add("Professional and Technology");
        categories.add("Colleges and Education");
        categories.add("Sports and Outdoors");
        categories.add("Youth and New Generation");
        categories.add("Social and Political");
        categories.add("Religious");
        categories.add("Others");

        // spinner for mode of event
        List<String> modes = new ArrayList<String>();
        modes.add("< Select Your Mode >");
        modes.add("Private");
        modes.add("Public");

        //spinner for event type
        List<String> type = new ArrayList<String>();
        type.add("< Select Your Event Type >");
        type.add("Registration");
        type.add("Tickets");
        type.add("RSVP");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modes);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter1);
        spinner3.setAdapter(dataAdapter2);
    }


    private void createNewevents(final String name, final String editext1, final String editext2, final String ewhere, final String spinner3, final String spinner, final String speaker, final String price, final String spinner2, final String imageview, final String description,final String email ){
        final ProgressDialog progressDialog = new ProgressDialog(editevents.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Event");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String uRl = Constants.URL_EDIT_EVENTS ;
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Sucessfully Changed")){
                    progressDialog.dismiss();
                    Toast.makeText(editevents.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(editevents.this,home.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(editevents.this,response,Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(editevents.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("name",name);
                param.put("eventstart",editext1);
                param.put("eventends",editext2);
                param.put("ewhere",ewhere);
                param.put("type",spinner3);
                param.put("category",spinner);
                param.put("speaker",speaker);
                param.put("price",price);
                param.put("mode",spinner2);
                param.put("photo",imageview);
                param.put("description",description);
                param.put("email",email);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(editevents.this).addToRequestQueue(request);

    }





    //choose images
    private void chooseImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    //image view
    private void view() {
        if(imageview == null)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}



