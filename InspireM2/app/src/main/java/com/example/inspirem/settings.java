package com.example.inspirem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void change(View view) {
        startActivity(new Intent(settings.this,resetpassword.class));
        finish();
    }


    public void about(View view) {
        Intent intent = new Intent(getApplicationContext(),about.class);
        startActivity(intent);
    }

    public void delete(View view) {
        startActivity(new Intent(settings.this,deleteaccount.class));
        finish();
    }
}
