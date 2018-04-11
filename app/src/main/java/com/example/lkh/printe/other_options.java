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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lkh.printe.Common.Common;
import com.example.lkh.printe.Remote.APIService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class other_options extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;
    private String document_link = "";
    private String shop_ID;
    private String printer_location;
    private EditText no_copies;
    private RadioGroup rg_type;
    private CheckBox double_sided;
    private RadioButton type_status;
    private String coloured = "0";
    private String port = "1";
    private String job_id;
    private String document_name;
    private String full_name;
    private RadioGroup orien;
    private String pages = "Contact Shop";
    String copies = "1";
    private APIService mService;

    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mService = Common.getFCMService();


        document_link = getIntent().getExtras().getString("document_lin");
        printer_location = getIntent().getExtras().getString("printer_location");
        shop_ID = getIntent().getExtras().getString("shop_ID");
        document_name = getIntent().getExtras().getString("document_name");
        full_name = getIntent().getExtras().getString("full_link");
        pages = getIntent().getExtras().getString("pages");
        orien = (RadioGroup) findViewById(R.id.radioGroup1);


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

        orien.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_type.getCheckedRadioButtonId();
                type_status = (RadioButton) findViewById(selectedId);
                String status = type_status.getText().toString();

                Log.e("sta",status);
                if (status.equals("Portrait"))
                {
                    port = "1";
                }
                else
                {
                    port = "0";
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

        copies = no_copies.getText().toString();
        if (copies.equals(""))
        {
            copies = "1";
        }
        Log.e("copies",copies);
        String double_checked;
        Calendar c = Calendar.getInstance();
        Log.e("time",c.getTime().toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Log.e("full--",formattedDate);
        if (double_sided.isChecked())
        {
            double_checked = "1";
        }
        else
        {
            double_checked = "0";
        }
        Log.e("double_checked",double_checked);

        final ProgressDialog progressDialog = ProgressDialog.show(other_options.this, "Please wait...", "Confirming....", true);

        String user_id = firebaseAuth.getCurrentUser().getUid();

        int price = 0;
        if (!pages.equals("Contact shop"))
        {
            if (coloured.equals("1"))
            {
                price = 5 * Integer.parseInt(pages) * Integer.parseInt(copies);
            }
            else {
                price = Integer.parseInt(pages) * Integer.parseInt(copies);
            }
            pages = Integer.toString(price);
        }


        db_reference.child("job_ids").setValue(String.valueOf(Integer.valueOf(job_id)+ 1 ));
        save_job_information save_job = new save_job_information(job_id,user_id,shop_ID,double_checked,copies, coloured, port, document_link, pages, document_name, formattedDate, "-99");
        db_reference.child("jobs").child(job_id).setValue(save_job);

        progressDialog.dismiss();

        sendNotificationJob(job_id);



        Toast.makeText(other_options.this, "Successfully Submitted....", Toast.LENGTH_LONG).show();
       finish();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


            StorageReference desertRef = mStorageReference.child(full_name);

            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });

    }







    private void sendNotificationJob(final String order_number) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        Query data=tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()) {
                    if (postSnapShot.getKey().equals(shop_ID)) {


                    Token serverToken = postSnapShot.getValue(Token.class);


                    Notification notification = new Notification("Printout ID- " + job_id + " and Name- " + document_name + " . Please click to open app and complete.", "You have new printout order ");
                    Sender content = new Sender(serverToken.getToken(), notification);

                    Log.e("token", serverToken.getToken());
                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {


                                    if (response.code() == 200) { //Run only when get Result
                                        if (response.body().success == 1) {
//                                            Toast.makeText(other_options.this, "Thank You, Order Placed", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(other_options.this, "Failed !!!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }


                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                    Log.e("ERROR", t.getMessage());

                                }
                            });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
