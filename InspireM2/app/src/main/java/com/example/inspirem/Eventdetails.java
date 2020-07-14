package com.example.inspirem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class Eventdetails extends AppCompatActivity {

    String FinalHttpData = "";
    BufferedWriter bufferedWriter ;
    WebCallParse webCallParse = new WebCallParse();
    BufferedReader bufferedReader ;
    OutputStream outputStream ;
    StringBuilder stringBuilder = new StringBuilder();
    String Result ;
    ListView SubjectListView;
    ProgressDialog pDialog;
    String HttpURL = Constants.URL_EVENTDETAILS;
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    URL url;
    List<String> listString = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter ;
    String FinalJSonObject ;
    Button btnupdate,btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventdetails);

        btndelete=(Button)findViewById(R.id.btndelete);

        SubjectListView = (ListView)findViewById(R.id.listview1);

        //Receiving the ListView Clicked item value send by previous activity.
        String TempItem = getIntent().getStringExtra("ListViewValue");

        //Calling method.
        HttpWebCall(TempItem);

        SubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Toast.makeText(Eventdetails.this, listString.get(position).toString(),Toast.LENGTH_LONG).show();

            }
        });


    }


    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(Eventdetails.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String.
                new GetHttpResponse(Eventdetails.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("CategoryName",params[0]);

                ParseResult = webCallParse.postRequest(ResultHash);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }

    public class WebCallParse {

        public String postRequest(HashMap<String, String> Data) {

            try {
                url = new URL(HttpURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(12000);

                httpURLConnection.setConnectTimeout(12000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(FinalDataParse(Data));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    httpURLConnection.getInputStream()
                            )
                    );
                    FinalHttpData = bufferedReader.readLine();
                }
                else {
                    FinalHttpData = "Something Went Wrong";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return FinalHttpData;
        }

        public String FinalDataParse(HashMap<String, String> hashMap2) throws UnsupportedEncodingException {

            for(Map.Entry<String, String> map_entry : hashMap2.entrySet()){

                stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));

            }

            Result = stringBuilder.toString();

            return Result ;
        }
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            //listString.add(jsonObject.getString("name").toString()) ;
                           // String name="\n\n\t\t"+"EVENT NAME"+"\t\t"+":"+"\t\t" +jsonObject.getString( "name").toUpperCase()+"\n\n\t\t"+"LOCATION"+"\t\t"+"\t\t\t"+":"+"\t\t" +jsonObject.getString( "ewhere").toUpperCase()+"\n\n\t\t"+"PRICE"+"\t\t\t\t\t\t\t\t\t"+":"+"\t\t" +jsonObject.getString( "price").toUpperCase()+"\n\n";
                            String name="\n\n\t\t"+"EVENT NAME"+"\t\t"+":"+"\t\t" +jsonObject.getString( "name").toUpperCase()+"\n\n\t\t"+"EVENT START"+"\t"+"\t"+":"+"\t\t" +jsonObject.getString( "eventstart").toUpperCase()+"\n\n\t\t"+"EVENT ENDS"+"\t\t"+"\t"+":"+"\t\t" +jsonObject.getString( "eventends").toUpperCase()+"\n\n\t\t"+"LOCATION"+"\t\t\t"+"\t\t"+":"+"\t\t" +jsonObject.getString( "ewhere").toUpperCase()+"\n\n\t\t"+"TYPE"+"\t\t\t"+"\t\t\t\t\t\t\t"+":"+"\t\t" +jsonObject.getString( "type").toUpperCase()+"\n\n\t\t"+"CATEGORY"+"\t\t"+"\t\t\t"+":"+"\t\t" +jsonObject.getString( "category").toUpperCase()+"\n\n\t\t"+"SPEAKER"+"\t\t\t"+"\t\t\t"+":"+"\t\t" +jsonObject.getString( "speaker").toUpperCase()+"\n\n\t\t"+"PRICE"+"\t\t\t\t\t"+"\t\t\t\t"+":"+"\t\t" +jsonObject.getString( "price").toUpperCase()+"\n\n\t\t"+"MODE"+"\t\t\t\t\t"+"\t\t\t\t"+":"+"\t\t" +jsonObject.getString( "mode").toUpperCase()+"\n\n\t\t";
                            listString.add(name) ;
                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            arrayAdapter = new ArrayAdapter<String>(Eventdetails.this,android.R.layout.simple_list_item_2, android.R.id.text1, listString);

            SubjectListView.setAdapter(arrayAdapter);

        }
    }

}
