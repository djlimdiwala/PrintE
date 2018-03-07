package com.example.lkh.printe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class document_upload extends AppCompatActivity {


    private String printer_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        printer_location = getIntent().getExtras().getString("selected-item");



    }



    public void showfilechooser()
    {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

    }


    public void go_next (View v)
    {
        Intent intent = new Intent(document_upload.this, other_options.class);
        intent.putExtra("printer_location",printer_location);
        intent.putExtra("document_link", "dummy-link");
        startActivity(intent);
    }

}
