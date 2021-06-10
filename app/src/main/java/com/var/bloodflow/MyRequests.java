package com.var.bloodflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyRequests extends AppCompatActivity {
    ImageButton fab_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyRequests.this, MakeRequest.class);
                startActivity(i);
            }
        });
    }
}