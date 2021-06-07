package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class tutorRegister extends AppCompatActivity {
    CheckBox e_bio,e_chem,s_bio,s_chem;
    Button ok_tutor;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String val="",userID;
    int count=0,error=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_register);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        userID=mAuth.getCurrentUser().getUid();
        e_bio=findViewById(R.id.c_e_bio);
        e_chem=findViewById(R.id.c_e_chem);
        s_bio=findViewById(R.id.c_s_bio);
        s_chem=findViewById(R.id.c_s_chem);
        ok_tutor=findViewById(R.id.tutor_btn);
        count=0;
//.................................................................................................................
//................................DISABLING CHECKBOXES.........................................................
//...................................................................................................................
        DocumentReference docRef = db.collection("courses").document("Eight").collection("sub").document("Biology");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String val=document.getString("tutor");
                        if(val.isEmpty()){

                        }else {
                            e_bio.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(tutorRegister.this, "No Document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(tutorRegister.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        DocumentReference docRef1 = db.collection("courses").document("Seven").collection("sub").document("Biology");
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        val=document.getString("tutor");
                        if(val.isEmpty()){

                        }else {
                            s_bio.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(tutorRegister.this, "No Document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(tutorRegister.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        DocumentReference docRef2 = db.collection("courses").document("Eight").collection("sub").document("Chemistry");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String val=document.getString("tutor");
                        if(val.isEmpty()){

                        }else {
                            e_chem.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(tutorRegister.this, "No Document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(tutorRegister.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        DocumentReference docRef3 = db.collection("courses").document("Seven").collection("sub").document("Chemistry");
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String val=document.getString("tutor");
                        if(val.isEmpty()){

                        }else {
                            s_chem.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(tutorRegister.this, "No Document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(tutorRegister.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

ok_tutor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        error=0;
        if(s_bio.isChecked()){
            count++;
        }
        if(s_chem.isChecked()){
            count++;
        }
        if(e_bio.isChecked()){
            count++;
        }
        if(e_chem.isChecked()){
            count++;
        }
        if(count!=1){
            Toast.makeText(tutorRegister.this, "CHOOSE ONLY ONE!!", Toast.LENGTH_SHORT).show();
            count=0;
        }else {
            if(s_bio.isChecked()){
                DocumentReference docRef4=db.collection("users").document(userID).collection("course").document("s_bio");
                Map<String,Object> user=new HashMap<>();
                user.put("course","Seven Biology");
                user.put("class","Seven");
                user.put("subject","Biology");
                docRef4.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
                DocumentReference docRef5=db.collection("courses").document("Seven").collection("sub").document("Biology");
                docRef5.update("tutor",userID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(tutorRegister.this, "Seven Biology Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error Seven Biology "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
            }
            if(s_chem.isChecked()){
                DocumentReference docRef6=db.collection("users").document(userID).collection("course").document("s_chem");
                Map<String,Object> user=new HashMap<>();
                user.put("course","Seven Chemistry");
                user.put("class","Seven");
                user.put("subject","Chemistry");
                docRef6.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
                DocumentReference docRef7=db.collection("courses").document("Seven").collection("sub").document("Chemistry");
                docRef7.update("tutor",userID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error Seven Chemistry "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
            }
            if(e_bio.isChecked()){
                DocumentReference docRef8=db.collection("users").document(userID).collection("course").document("e_bio");
                Map<String,Object> user=new HashMap<>();
                user.put("course","Eight Biology");
                user.put("class","Eight");
                user.put("subject","Biology");
                docRef8.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
                DocumentReference docRef9=db.collection("courses").document("Eight").collection("sub").document("Biology");
                docRef9.update("tutor",userID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error Eight Biology "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
            }
            if(e_chem.isChecked()){
                DocumentReference docRef0=db.collection("users").document(userID).collection("course").document("e_chem");
                Map<String,Object> user=new HashMap<>();
                user.put("course","Eight Chemistry");
                user.put("class","Eight");
                user.put("subject","Chemistry");
                docRef0.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
                DocumentReference docReff=db.collection("courses").document("Eight").collection("sub").document("Chemistry");
                docReff.update("tutor",userID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tutorRegister.this, "Error Eight Chemistry "+e.toString(), Toast.LENGTH_SHORT).show();
                        error=1;
                    }
                });
            }
            if(error!=0){
                Toast.makeText(tutorRegister.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(tutorRegister.this, "Success :)", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

             //.........................................
        }





    }
});
}
}