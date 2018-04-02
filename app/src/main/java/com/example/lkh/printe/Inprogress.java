package com.example.lkh.printe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress);


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

        timee.setText(getIntent().getExtras().getString("time"));
        JobID.setText(getIntent().getExtras().getString("jobID"));
        file_name.setText(getIntent().getExtras().getString("file"));
        loc.setText(getIntent().getExtras().getString("location"));
        ownername.setText(getIntent().getExtras().getString("owner"));
        owner_mobile.setText(getIntent().getExtras().getString("contact"));
        no_copies.setText(getIntent().getExtras().getString("copies"));
        col.setText(getIntent().getExtras().getString("cld"));
        dou_side.setText(getIntent().getExtras().getString("ds"));


    }


    public void show_doc (View v){

        String path = preferences.getString(file_name.getText().toString(),"");

        File file = new File(path);
        Uri webpage = Uri.parse(d_link);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, webpage);
        intent.setDataAndType(Uri.parse(d_link), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
