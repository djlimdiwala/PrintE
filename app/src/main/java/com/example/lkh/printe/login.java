package com.example.lkh.printe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {


    private EditText email;
    private EditText passwd;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void login_user(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(login.this, "Please wait...", "Loging in...", true);

        email = (EditText) findViewById(R.id.email);
        passwd = (EditText) findViewById(R.id.passwd);
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), passwd.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            db_reference = FirebaseDatabase.getInstance().getReference();
                            String id1 = firebaseAuth.getCurrentUser().getUid();
                            db_reference.child("users").child(id1).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            save_user_information user = dataSnapshot.getValue(save_user_information.class);



                                            progressDialog.dismiss();
                                            Intent intent = new Intent(login.this, Home.class);
                                            intent.putExtra("mail", user.email);
                                            intent.putExtra("name", user.name);
                                            startActivity(intent);
                                            finish();

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            email.setText("");
                                            passwd.setText("");
                                            Toast.makeText(login.this, "Connection lost...Try again",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        else {
                            Log.e("ERROR", task.getException().toString());
                            progressDialog.dismiss();
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }


}
