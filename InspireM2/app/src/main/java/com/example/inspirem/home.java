package com.example.inspirem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.Authenticator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* created by jisto jose on 10-02-2020 */
public class home extends AppCompatActivity {
    //private static final String url=Constants.URL_HOME;
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVEventPrice;
    private AdapterEvent mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new AsyncLogin().execute();
/*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<ja.length){
                    Intent intent = new Intent(getApplicationContext(), Eventdetails.class);
                    startActivity(intent);

                }
            }
        });

*/


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
                       startActivity(new Intent (getApplicationContext(),profile.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.home:
                       return true;
               }
                return false;
            }
        });

    }



    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(home.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL(Constants.URL_HOME);

            } catch ( MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataEvent> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataEvent Dataevent = new DataEvent();
                   // fishData.fishImage= json_data.getString("fish_img");
                    Dataevent.EventName= json_data.getString("name").toUpperCase();
                    Dataevent.Eventwhere= json_data.getString("ewhere").toUpperCase();
                    Dataevent.Speaker= json_data.getString("speaker").toUpperCase();
                    Dataevent.price= json_data.getInt("price");
                    Dataevent.UserName= json_data.getString("username");
                    Dataevent.EventStart= json_data.getString("eventstart").toUpperCase();
                    Dataevent.EventEnds= json_data.getString("eventends").toUpperCase();
                    Dataevent.ContactNum= json_data.getString("phone");
                    data.add(0,Dataevent);
                }

                // Setup and Handover data to recyclerview
                mRVEventPrice = (RecyclerView)findViewById(R.id.EventList);
                mAdapter = new AdapterEvent(home.this, data);
                mRVEventPrice.setAdapter(mAdapter);
                mRVEventPrice.setLayoutManager(new LinearLayoutManager(home.this));

            } catch (JSONException e) {
                Toast.makeText(home.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }



    /*
    private void getdata() {
        model = new Model("Jisto","55");
        list.add(model);
        model = new Model("Jophin","95");
        list.add(model);
        model = new Model("Mathew","585");
        list.add(model);
        model = new Model("Anson","55");
        list.add(model);
        model = new Model("Jobin","59");
        list.add(model);
        model = new Model("Sam","55");
        list.add(model);
        model = new Model("alen","95");
        list.add(model);
        model = new Model("dony","585");
        list.add(model);
        model = new Model("jobin","55");
        list.add(model);
        model = new Model("nikhil","59");
        list.add(model);

    }

*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_events){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getApplicationContext(),profile.class);
            //startActivity(intent);

        }
        if(item.getItemId()==R.id.myevents){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),myevents.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.editevents){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),editevents.class);
            startActivity(intent);

        }


        if(item.getItemId()==R.id.appliedevents){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),appliedevents.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.editevents){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),editevents.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.action_settings){
            //Toast.makeText(this,"welcome to login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),settings.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.action_logout){
            //Toast.makeText(this,"you are logouting",Toast.LENGTH_SHORT).show();
            //firebaseAuth.signOut();
            //onStart();
            logout();
        }



        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        //startActivity(new Intent(getApplicationContext(),login.class));
       // overridePendingTransition(0,0);

        AlertDialog.Builder builder=new AlertDialog.Builder(home.this); //Home is name of the activity
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                finish();
                Intent i=new Intent(home.this,login.class);
                i.putExtra("finish", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity(i);
                finish();

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sidemenu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed(){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }


    public void fab(View view) {

        FloatingActionButton fab = findViewById(R.id.fab);
        Snackbar.make(view, "Create Your Event", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        startActivity(new Intent(getApplicationContext(),create_events.class));
            }


    public void details(View view) {
        Intent intent = new Intent(getApplicationContext(), Eventdetails.class);
        startActivity(intent);

    }
}

