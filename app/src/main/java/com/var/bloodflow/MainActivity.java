package com.var.bloodflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import static com.var.bloodflow.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(activity_main);

        Thread thread = new Thread(){
            public void run(){
                try {
                        sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(com.var.bloodflow.MainActivity.this, com.var.bloodflow.Login.class);
                    startActivity(intent);
                }
            }
        };thread.start();


    }
}
