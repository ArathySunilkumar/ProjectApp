package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class studentRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button reg;
    String st_class;
    String userID,name;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    int error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        fstore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        reg=(Button)findViewById(R.id.class_btn);

        Spinner st_spinner=findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(studentRegister.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.studentclass));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        st_spinner.setAdapter(myAdapter);
        st_spinner.setOnItemSelectedListener(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error=0;
                userID=mAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fstore.collection("users").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            name=document.getString("name");
                            DocumentReference documentReference1=fstore.collection("courses").document(st_class).collection("sub").document("Biology").collection("students").document(userID);
                            Map<String,Object> user1=new HashMap<>();
                            user1.put("name",name);
                            documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(studentRegister.this, "ERROR : "+e.toString(), Toast.LENGTH_SHORT).show();
                                    error=1;
                                }
                            });
                            DocumentReference documentReference2=fstore.collection("courses").document(st_class).collection("sub").document("Chemistry").collection("students").document(userID);
                            Map<String,Object> user2=new HashMap<>();
                            user2.put("name",name);
                            documentReference2.set(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(studentRegister.this, "ERROR : "+e.toString(), Toast.LENGTH_SHORT).show();
                                    error=1;
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(studentRegister.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                //..........................................................................................
                Map<String,Object> user=new HashMap<>();
                user.put("class",st_class);
                documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(studentRegister.this, "ERROR : "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });

                if(error!=0){
                    Toast.makeText(studentRegister.this, "Error!!", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(studentRegister.this, "Success :)", Toast.LENGTH_SHORT).show();
                    Toast.makeText(studentRegister.this,  "user added", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        st_class=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}