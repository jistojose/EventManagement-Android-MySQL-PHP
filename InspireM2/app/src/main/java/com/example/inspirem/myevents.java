package com.example.inspirem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_2;

/* created by jisto jose on 10-02-2020 */
public class myevents extends AppCompatActivity {
    private static final String url=Constants.URL_MYEVENTS;
    ListView lv;
    Bundle b;
    TextView email;
    //String email,password;
    ArrayList<String>holder1=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevents);


        // to avoid actionbar
        getSupportActionBar().setTitle("My Events");
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);







        fetchdata();
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

    }



    private void fetchdata() {
        lv=(ListView)findViewById(R.id.lv);



        class dbManager extends AsyncTask<String,Void,String>
        {


            protected void onPostExecute(String data)
            {
                try{

                    SharedPreferences bb = getSharedPreferences("my_prefs", 0);
                    final String m = bb.getString("email", "");
                   // email.setText(m);



                    final JSONArray ja=new JSONArray(data);
                    JSONObject jo=null;

                        for(int i=0;i<ja.length();i++)
                    {

                       // if (jo.getString("email").equals(myevents.this.email));
                      //  {


                        jo=ja.getJSONObject(i);


                        if (jo.getString("email").equals(m)) {


                        //String name="\n\n\t\t"+"EVENT NAME"+"\t\t"+":"+"\t\t" +jo.getString( "name").toUpperCase()+"\n\n\t\t"+"EVENT START"+"\t"+"\t"+":"+"\t\t" +jo.getString( "eventstart").toUpperCase()+"\n\n\t\t"+"EVENT ENDS"+"\t\t"+"\t"+":"+"\t\t" +jo.getString( "eventends").toUpperCase()+"\n\n\t\t"+"LOCATION"+"\t\t\t"+"\t\t"+":"+"\t\t" +jo.getString( "ewhere").toUpperCase()+"\n\n\t\t"+"TYPE"+"\t\t\t"+"\t\t\t\t\t\t\t"+":"+"\t\t" +jo.getString( "type").toUpperCase()+"\n\n\t\t"+"CATEGORY"+"\t\t"+"\t\t\t"+":"+"\t\t" +jo.getString( "category").toUpperCase()+"\n\n\t\t"+"SPEAKER"+"\t\t\t"+"\t\t\t"+":"+"\t\t" +jo.getString( "speaker").toUpperCase()+"\n\n\t\t"+"PRICE"+"\t\t\t\t\t"+"\t\t\t\t"+":"+"\t\t" +jo.getString( "price").toUpperCase()+"\n\n\t\t"+"MODE"+"\t\t\t\t\t"+"\t\t\t\t"+":"+"\t\t" +jo.getString( "mode").toUpperCase()+"\n\n\t\t";
                            String name=jo.getString( "name").toUpperCase();

                        holder1.add(0,name);
                        }
                    }


                   // }


                    ArrayAdapter<String> at=new ArrayAdapter<String>(getApplicationContext(),simple_list_item_2, android.R.id.text1, holder1);

                    lv.setAdapter(at);
                    at.notifyDataSetChanged();
                    lv.smoothScrollToPosition(0);



                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            // TODO Auto-generated method stub

                            Intent intent = new Intent(getApplicationContext(),Eventdetails.class);

                            intent.putExtra("ListViewValue", holder1.get(position).toString());

                            startActivity(intent);

                        }
                    });









                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected String doInBackground(String... strings)
            {
                try{
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data=new StringBuffer();
                    String line;
                    while ((line=br.readLine())!=null)
                    {
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }catch(Exception ex){
                    return ex.getMessage();
                }
            }
        }
        dbManager obj=new dbManager();
        obj.execute(url);
    }










}


