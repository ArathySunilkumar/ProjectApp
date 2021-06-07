package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tutorHome extends AppCompatActivity {
ImageButton logout;
ImageButton student,course,work;
TextView tutorName;
FirebaseAuth fAuth;
FirebaseFirestore db;
String userID,name="Name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);
        logout=findViewById(R.id.btn_logout);
        student=findViewById(R.id.btn_student);
        course=findViewById(R.id.btn_course);
        work=findViewById(R.id.btn_work);
        tutorName=findViewById(R.id.view_name);
        fAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        try{
            DocumentReference df=db.collection("users").document(userID);
            df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc=task.getResult();
                        if(doc.exists()){
                            name=doc.getString("name");
                            tutorName.setText(name);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(tutorHome.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });


           tutorName.setText(name);//............................set tutor name at top
        }catch (Exception e){
            Toast.makeText(this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
           // tutorName.setText(e.toString());
        }



//................................................................................................................................
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent=new Intent(getApplicationContext(),viewStudents.class);
                    Bundle b=new Bundle();
                    b.putString("userID",userID);
                    intent.putExtras(b);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(tutorHome.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
//................................................................................................................................
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}