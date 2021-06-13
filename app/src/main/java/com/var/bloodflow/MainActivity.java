package com.var.bloodflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.var.bloodflow.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(activity_main);

        Thread thread = new Thread() {
            public void run() {

                try {
                    sleep(2000);
//                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                } finally {
                    sp = getSharedPreferences("Credentials", Context.MODE_PRIVATE);
                    String name = sp.getString("Name", "");
                    Log.d("Name: ", name);
                    if (sp.getInt("Log in Status", 0) == 2) {
                        Intent check = new Intent(MainActivity.this, Nav.class);
                        startActivity(check);
                        finish();
                    } else if (!sp.contains("Phone Number")) {
                        Intent register = new Intent(MainActivity.this, Login.class);
                        startActivity(register);
                        Toast.makeText(MainActivity.this, "Phn no & details not collected ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String phone = sp.getString("Phone Number", "");
                        Intent register = new Intent(MainActivity.this, Login.class);
                        register.putExtra("Phone Number", phone);
                        startActivity(register);
                        Toast.makeText(MainActivity.this, "idk ", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        };
        thread.start();
    }


}

//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("Image", "Image");
//        editor.putInt("User ID", 1);
//        editor.putInt("Log in Status", 0);
//        editor.apply();

