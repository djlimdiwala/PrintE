package com.example.lkh.printe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Completed extends AppCompatActivity {


    private TextView JobID;
    private TextView file_name;
    private TextView ownername;
    private TextView owner_mobile;
    private TextView loc;
    private TextView no_copies;
    private TextView col;
    private TextView dou_side;
    private TextView timee;
    private TextView ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);


        timee = (TextView) findViewById(R.id.timeee);
        JobID = (TextView) findViewById(R.id.jobID_value);
        file_name = (TextView) findViewById(R.id.document_name_value);
        loc = (TextView) findViewById(R.id.printer_location_value);
        ownername = (TextView) findViewById(R.id.Owner_name_value);
        owner_mobile = (TextView) findViewById(R.id.Owner_contact_value);
        no_copies = (TextView) findViewById(R.id.copies_value);
        col = (TextView) findViewById(R.id.color_value);
        dou_side = (TextView) findViewById(R.id.double_value);
        ct = (TextView) findViewById(R.id.com);

        timee.setText(getIntent().getExtras().getString("time"));
        JobID.setText(getIntent().getExtras().getString("jobID"));
        file_name.setText(getIntent().getExtras().getString("file"));
        loc.setText(getIntent().getExtras().getString("location"));
        ownername.setText(getIntent().getExtras().getString("owner"));
        owner_mobile.setText(getIntent().getExtras().getString("contact"));
        no_copies.setText(getIntent().getExtras().getString("copies"));
        col.setText(getIntent().getExtras().getString("cld"));
        dou_side.setText(getIntent().getExtras().getString("ds"));
        String temp = "Completed on\n" + getIntent().getExtras().getString("ctime");
        ct.setText(temp);
    }


}
