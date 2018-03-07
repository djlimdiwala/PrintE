package com.example.lkh.printe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class take_printout_online_printers extends AppCompatActivity {


   private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_printout_online_printers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        lv = (ListView)findViewById(R.id.printers);
        // Array holding our data
        String[] foody = {"H15 A wing", "H11", "H15 C wing", "H10 A wing", "H2"};
        //adapter which will convert each data item into view item.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.listText, foody);
        //place each view-item inside listview by setting adapter for our listview
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView listText = (TextView) view.findViewById(R.id.listText);
                String text = listText.getText().toString();
                Toast.makeText(take_printout_online_printers.this, text, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(take_printout_online_printers.this, document_upload.class);
                intent.putExtra("selected-item", text);
                startActivity(intent);
            }
        });
    }

}
