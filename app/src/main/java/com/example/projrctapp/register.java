package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText txt_email,txt_name,txt_phone,txt_pass,txt_pass1;
    Button signup;
    Switch isTutor;
   FirebaseAuth mAuth;
    String userID,type;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(getApplicationContext(),loginActivity.class);
            startActivity(intent);
            finish();
        }

        fstore=FirebaseFirestore.getInstance();

        txt_email=(EditText)findViewById(R.id.email_id);
        txt_name=(EditText)findViewById(R.id.name);
        txt_phone=(EditText)findViewById(R.id.phone);
        txt_pass=(EditText)findViewById(R.id.pass_1);
        txt_pass1=(EditText)findViewById(R.id.pass_2);
        isTutor=(Switch) findViewById(R.id.switch1);
        signup=(Button)findViewById(R.id.register_btn);



        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=txt_email.getText().toString();
                String pass=txt_pass.getText().toString();
                String pass1=txt_pass1.getText().toString();
                String name=txt_name.getText().toString();
                String phone=txt_phone.getText().toString();
                boolean tutor = isTutor.isChecked();
                if(tutor){
                    type="tutor";
                }
                else {
                    type="student";
                }

                if (TextUtils.isEmpty(email)) {
                    txt_email.setError("email required!!");
                }else{
                    if(TextUtils.isEmpty(name)){
                    txt_name.setError("Name Required!!");
                    }else {
                        if(TextUtils.isEmpty(phone)){
                            txt_phone.setError("Phone Number Required!!");
                        }else {
                            if(TextUtils.isEmpty(pass)){
                                txt_phone.setError("Password Required!!");
                            }else {
                                if(TextUtils.isEmpty(pass1)){
                                    txt_phone.setError("Re-enter Password!!");
                                }else {
                                    if(!pass.equals(pass1)){
                                        Toast.makeText(register.this, "PASSWORDS DON'T MATCH!!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    userID=mAuth.getCurrentUser().getUid();
                                                    DocumentReference documentReference=fstore.collection("users").document(userID);
                                                    Map<String,Object> user=new HashMap<>();
                                                    user.put("email",email);
                                                    user.put("name",name);
                                                    user.put("phone",phone);
                                                    user.put("type",type);
                                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(register.this, "User Created!!", Toast.LENGTH_SHORT).show();
                                                            if(type.equals("student")){
                                                                Intent intent=new Intent(getApplicationContext(),studentRegister.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            if(type.equals("tutor")){
                                                                Intent intent=new Intent(getApplicationContext(),tutorRegister.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d(null,"Failure :"+e.toString());
                                                        }
                                                    });
                                                }else {
                                                    Toast.makeText(register.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }



            }
        });
    }
}