package com.example.lkh.printe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class other_options extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;
    private String document_link;
    private String shop_ID;
    private String printer_location;
    private EditText no_copies;
    private RadioGroup rg_type;
    private CheckBox double_sided;
    private RadioButton type_status;
    private String coloured;
    private String job_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();
        document_link = getIntent().getExtras().getString("document_link");
        printer_location = getIntent().getExtras().getString("printer_location");
        shop_ID = getIntent().getExtras().getString("shop_ID");

        rg_type = (RadioGroup) findViewById(R.id.radioGroup);
        no_copies = (EditText) findViewById(R.id.copies);
        double_sided = (CheckBox) findViewById(R.id.double_sided);



        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_type.getCheckedRadioButtonId();
                type_status = (RadioButton) findViewById(selectedId);
                String status = type_status.getText().toString();

                Log.e("sta",status);
                if (status.equals("BnW"))
                {
                    coloured = "0";
                }
                else
                {
                    coloured = "1";
                }



            }

        });


        db_reference.child("job_ids").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        job_id = dataSnapshot.getValue().toString();
                        Log.e("job",job_id);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    public void submit_job (View v)
    {
        String copies = no_copies.getText().toString();
        Log.e("copies",copies);
        String double_checked;
        if (double_sided.isChecked())
        {
            double_checked = "1";
        }
        else
        {
            double_checked = "0";
        }
        Log.e("double_checked",double_checked);
//        Log.e("coloured",coloured);


        final ProgressDialog progressDialog = ProgressDialog.show(other_options.this, "Please wait...", "Confirming....", true);

        String user_id = firebaseAuth.getCurrentUser().getUid();
//        Log.e("id", user_id);




        db_reference.child("job_ids").setValue(String.valueOf(Integer.valueOf(job_id)+ 1 ));
        save_job_information save_job = new save_job_information(job_id,user_id,shop_ID,double_checked,copies, coloured, document_link, "dummy");
        db_reference.child("jobs").child(job_id).setValue(save_job);

        progressDialog.dismiss();
        Toast.makeText(other_options.this, "Successfully Submitted....", Toast.LENGTH_LONG).show();

    }

}
