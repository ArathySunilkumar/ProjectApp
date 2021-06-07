package com.example.projrctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button reg_page,log_page,testbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg_page=findViewById(R.id.btn_reg_page);
        log_page=findViewById(R.id.btn_log_page);
        testbtn=findViewById(R.id.button2);
//WHEN 'CREATE NEW ACCOUNT' IS CLICKED.
        reg_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                finish();
            }
        });
//WHEN 'LOG IN' IS CLICKED.
        log_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),loginPage.class);
                startActivity(intent);
                finish();
            }
        });

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),forTest.class);
                startActivity(intent);
                finish();
            }
        });

    }
}