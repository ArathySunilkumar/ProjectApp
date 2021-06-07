package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText l_email,l_password;
    Button l_button;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        l_email=(EditText)findViewById(R.id.login_email);
        l_password=(EditText)findViewById(R.id.login_password);
        l_button=(Button)findViewById(R.id.login_button);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=l_email.getText().toString();
                String password=l_password.getText().toString();

                if(TextUtils.isEmpty(email)){
                    l_email.setError("Email ID required");
                }else {
                    if(TextUtils.isEmpty(password)){
                        l_password.setError("Password required");
                    }else {
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    //.........................user exists.............................
                                    //.............is user student or tutor??...........................
                                    String userID=mAuth.getCurrentUser().getUid();
                                    db.collection("users").document(userID)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String type = doc.getString("type");
                                      //......................tutorHome...............................................
                                                        if (type.equalsIgnoreCase("tutor")) {
                                                            Toast.makeText(loginPage.this, "Logging in successful", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), tutorHome.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                      //......................studentHome...............................................
                                                        if (type.equalsIgnoreCase("student")) {
                                                            Toast.makeText(loginPage.this, "Logging in successful", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), studentHome.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });



                                }else {
                                    Toast.makeText(loginPage.this, "ERROR : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}