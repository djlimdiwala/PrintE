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

public class login extends AppCompatActivity {


    private EditText email;
    private EditText passwd;
    private FirebaseAuth firebaseAuth;
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
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(login.this, Home.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        }
                        else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }


}
