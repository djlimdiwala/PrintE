package com.example.lkh.printe;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Inprogress extends AppCompatActivity {

    private TextView JobID;
    private TextView file_name;
    private TextView ownername;
    private TextView owner_mobile;
    private TextView loc;
    private TextView no_copies;
    private TextView col;
    private TextView dou_side;
    private TextView timee;
    private String d_link;
    public SharedPreferences preferences;
    public TextView por;
    private Button can;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;
    private save_job_information job;


    private String j1;
    private String j2;
    private String j3;
    private String j4;
    private String j5;
    private String j6;
    private String j7;
    private String j8;
    private String j9;
    private String j10;
    private String j11;
    private String j12;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();

        preferences = getApplicationContext().getSharedPreferences("MyPref", 0);

        timee = (TextView) findViewById(R.id.timeee);
        JobID = (TextView) findViewById(R.id.jobID_value);
        file_name = (TextView) findViewById(R.id.document_name_value);
        loc = (TextView) findViewById(R.id.printer_location_value);
        ownername = (TextView) findViewById(R.id.Owner_name_value);
        owner_mobile = (TextView) findViewById(R.id.Owner_contact_value);
        no_copies = (TextView) findViewById(R.id.copies_value);
        col = (TextView) findViewById(R.id.color_value);
        dou_side = (TextView) findViewById(R.id.double_value);
        d_link = getIntent().getExtras().getString("l_link");
        por = (TextView) findViewById(R.id.port1_value);
        can = (Button) findViewById(R.id.cancel);


        j1 = getIntent().getExtras().getString("jobID");
        j2 = getIntent().getExtras().getString("u_id");
        j3 = getIntent().getExtras().getString("s_id");
        j4 = getIntent().getExtras().getString("ds1");
        j5 = getIntent().getExtras().getString("copies");
        j6 = getIntent().getExtras().getString("cld1");
        j7 = getIntent().getExtras().getString("port1");
        j8 = getIntent().getExtras().getString("l_link");
        j9 = getIntent().getExtras().getString("pag");
        j10 = getIntent().getExtras().getString("file");
        j11 = getIntent().getExtras().getString("creat");
        j12 = getIntent().getExtras().getString("end");





        timee.setText(getIntent().getExtras().getString("time"));
        JobID.setText(getIntent().getExtras().getString("jobID"));
        file_name.setText(getIntent().getExtras().getString("file"));
        loc.setText(getIntent().getExtras().getString("location"));
        ownername.setText(getIntent().getExtras().getString("owner"));
        owner_mobile.setText(getIntent().getExtras().getString("contact"));
        no_copies.setText(getIntent().getExtras().getString("copies"));
        col.setText(getIntent().getExtras().getString("cld"));
        dou_side.setText(getIntent().getExtras().getString("ds"));
        por.setText(getIntent().getExtras().getString("port"));




        can.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Inprogress.this);
                builder1.setMessage("Are you sure you want to cancel??...");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {



                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = df.format(c.getTime());

                                save_job_information save_job = new save_job_information(j1,j2,j3,j4,j5,j6,j7,j8,j9,j10,j11,formattedDate);


                                db_reference.child("jobs").child(j1).setValue(save_job);

                                StorageReference desertRef = mStorageReference.child(d_link);

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
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


    }


    public void show_doc (View v){

//        String path = preferences.getString(file_name.getText().toString(),"");
//
//        File file = new File(path);
        Uri webpage = Uri.parse(d_link);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, webpage);
        intent.setDataAndType(Uri.parse(d_link), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
