package com.geohot.aplikasiarisan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.e("TAG", "onCreate: "+getIntent().getStringExtra("body"));
        TextView judul=findViewById(R.id.judul);
        judul.setText(getIntent().getStringExtra("title"));
        TextView message=findViewById(R.id.message);
        message.setText(getIntent().getStringExtra("body"));
    }

}