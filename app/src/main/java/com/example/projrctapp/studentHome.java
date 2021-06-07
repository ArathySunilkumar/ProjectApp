package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class studentHome extends AppCompatActivity {
TextView txtname,txtchoose;
Button btn_bio,btn_chem,btn_exit;
FirebaseAuth fAuth;
FirebaseFirestore db;
String name,userID,cls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        txtname=findViewById(R.id.view_st_name);
        txtchoose=findViewById(R.id.textView10);
        btn_bio=findViewById(R.id.gobio);
        btn_chem=findViewById(R.id.gochem);
        btn_exit=findViewById(R.id.st_home_logout);
//...................................................SET TEXTVIEW WHITE COLOR
        txtname.setTextColor(Color.WHITE);
        txtchoose.setTextColor(Color.WHITE);
//......................................................................
        fAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();

        Bundle b=new Bundle();
        b.putString("userID",userID);

        Bundle b1=new Bundle();
        b1.putString("userID",userID);

        try{
            DocumentReference df=db.collection("users").document(userID);
            df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc=task.getResult();
                        if(doc.exists()){
                            name=doc.getString("name");
                            cls=doc.getString("class");
                            txtname.setText("Welcome "+name);
                            b.putString("Name",name);
                            b1.putString("Name",name);
                            b.putString("Class",cls);
                            b1.putString("Class",cls);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(studentHome.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

//................................BIOLOGY...................................................................

        btn_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),studentSecondHome.class);
                b.putString("subject","Biology");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

//................................CHEMISTRY.................................................................

        btn_chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),studentSecondHome.class);
                b1.putString("subject","Chemistry");
                intent.putExtras(b1);
                startActivity(intent);
            }
        });

//.................................SIGN OUT..................................................................

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}