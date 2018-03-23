package com.example.lkh.printe;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_profile extends AppCompatActivity {

    private EditText email_text;
    private FirebaseAuth firebaseAuth;
    private EditText user_name;
    private EditText mobile_no;
    private EditText hostel_no;
    private EditText room_no;
    private EditText pass_word;
    private DatabaseReference db_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();
        user_name = (EditText) findViewById(R.id.name);
        hostel_no = (EditText) findViewById(R.id.hostel);
        mobile_no = (EditText) findViewById(R.id.mobile);
        room_no = (EditText) findViewById(R.id.room);
        email_text = (EditText) findViewById(R.id.email);

        user_name.setText(getIntent().getExtras().getString("name"));
        email_text.setText(getIntent().getExtras().getString("mail"));
        mobile_no.setText(getIntent().getExtras().getString("mobile"));
        room_no.setText(getIntent().getExtras().getString("room"));
        hostel_no.setText(getIntent().getExtras().getString("hostel"));





    }



    public void edit_user(View v) {


        final String name = user_name.getText().toString();
        final String Email = email_text.getText().toString();
        final String m_no = mobile_no.getText().toString();
        final String h_no = hostel_no.getText().toString();
        final String r_no = room_no.getText().toString();
        int flag = 1;

        if (name.length() == 0)
        {
            flag = 0;
            user_name.setError("Enter First Name");
            user_name.requestFocus();
            Log.e("11","11");
        }
        else if (!email_validate(Email))
        {
            flag = 0;
            email_text.setError("Invalid Email");
            email_text.requestFocus();
            Log.e("22","22");
        }
        else if (!mobile_validate(m_no))
        {
            flag = 0;
            Log.e("33","33");
            mobile_no.setError("Invalid mobile");
            mobile_no.requestFocus();
        }
        else if (h_no.length() == 0)
        {
            flag = 0;
            Log.e("44","44");
            hostel_no.setError("Enter hostel no.");
            hostel_no.requestFocus();
        }
        else if (r_no.length() == 0)
        {
            flag = 0;
            Log.e("55","55");
            room_no.setError("Enter room no.");
            room_no.requestFocus();
        }



        if (flag == 1) {


            final ProgressDialog progressDialog = ProgressDialog.show(Edit_profile.this, "Please wait...", "Regsitering...", true);



            String id1 = firebaseAuth.getCurrentUser().getUid();

            save_user_information save_user = new save_user_information(name, Email, m_no, h_no, r_no);
            db_reference.child("users").child(id1).setValue(save_user);

            progressDialog.dismiss();
            Toast.makeText(this, "Successfully updated...", Toast.LENGTH_SHORT).show();



            finish();


        }
    }


    protected boolean email_validate(String mail)
    {
        if (TextUtils.isEmpty(mail))
        {
            return false;
        }
        else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }

    protected boolean mobile_validate(String mobile)
    {
        if (TextUtils.isEmpty(mobile))
        {
            return false;
        }
        else
        {
            return android.util.Patterns.PHONE.matcher(mobile).matches();
        }
    }



}
