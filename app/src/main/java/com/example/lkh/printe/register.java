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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {


    private EditText email_text;
    private EditText password_text;
    private FirebaseAuth firebaseAuth;
    private EditText user_name;
    private EditText mobile;
    private EditText hostel;
    private EditText room;
    private DatabaseReference db_reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_text = (EditText) findViewById(R.id.email);
        password_text = (EditText) findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();
        user_name = (EditText) findViewById(R.id.name);
        hostel = (EditText) findViewById(R.id.hostel);
        mobile = (EditText) findViewById(R.id.mobile);
        room = (EditText) findViewById(R.id.room);

    }

    public void register_user(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(register.this, "Please wait...", "Regsitering...", true);

        (firebaseAuth.createUserWithEmailAndPassword(email_text.getText().toString(), password_text.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "Registration successful", Toast.LENGTH_LONG).show();

                            String id1 = firebaseAuth.getCurrentUser().getUid();
                            String name = user_name.getText().toString();
                            String Email = email_text.getText().toString();
                            String m_no = mobile.getText().toString();
                            String h_no = hostel.getText().toString();
                            String r_no = room.getText().toString();

                            save_user_information save_user = new save_user_information(name, Email, m_no, h_no, r_no);
                            db_reference.child(id1).setValue(save_user);


                            Intent i = new Intent(register.this, login.class);
                            startActivity(i);
                        }
                        else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
