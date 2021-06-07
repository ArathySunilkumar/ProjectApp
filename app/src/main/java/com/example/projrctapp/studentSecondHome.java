package com.example.projrctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class studentSecondHome extends AppCompatActivity {
    TextView txtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_second_home);
        Bundle bundle = getIntent().getExtras();
        String userID= bundle.getString("userID");
        String name=bundle.getString("Name");
        String cls=bundle.getString("Class");
        String sub=bundle.getString("subject");

        txtname=findViewById(R.id.name_view);
        txtname.setText(sub);//..............subject name at top
        txtname.setTextColor(Color.WHITE);//...........text color white


    }
}