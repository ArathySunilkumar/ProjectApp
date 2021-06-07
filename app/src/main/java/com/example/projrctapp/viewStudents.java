package com.example.projrctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class viewStudents extends AppCompatActivity {
    FirebaseFirestore db;
    ListView studentsList;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String classOfTutor,subOfTutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        studentsList=(ListView)findViewById(R.id.studentss);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        studentsList.setAdapter(arrayAdapter);
        db=FirebaseFirestore.getInstance();

        try{
            Bundle bundle = getIntent().getExtras();
            String userID= bundle.getString("userID");
            db.collection("users").document(userID).collection("course")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc:task.getResult()){
                                    classOfTutor=doc.getString("class");
                                    subOfTutor=doc.getString("subject");
                                    //Toast.makeText(viewStudents.this, classOfTutor+" : "+subOfTutor, Toast.LENGTH_SHORT).show();
                                }
 //.........................................................................................................................................
                                //Toast.makeText(viewStudents.this, classOfTutor+" : "+subOfTutor, Toast.LENGTH_SHORT).show();

                                try{
                                    db.collection("courses").document(classOfTutor).collection("sub").document(subOfTutor).collection("students")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(viewStudents.this, "Processing", Toast.LENGTH_SHORT).show();
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            //Log.d(null, document.getId() + " => " + document.getData());
                                                            //Toast.makeText(forTest.this, "Name : "+document.getString("name"), Toast.LENGTH_SHORT).show();
                                                            String name=document.getString("name");

                                                            arrayList.add(name);
                                                            arrayAdapter.notifyDataSetChanged();
                                                        }
                                                    } else {
                                                        //Log.d(null, "Error getting documents: ", task.getException());
                                                        Toast.makeText(viewStudents.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }catch(Exception e){
                                    // Toast.makeText(this, "2 : "+classOfTutor+subOfTutor+e.toString(), Toast.LENGTH_SHORT).show();
                                }

 //.......................................................................................................................................
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(viewStudents.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "1 : "+e.toString(), Toast.LENGTH_SHORT).show();
        }









    }
}