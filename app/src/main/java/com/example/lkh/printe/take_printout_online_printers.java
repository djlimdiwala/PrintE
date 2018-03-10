package com.example.lkh.printe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class take_printout_online_printers extends AppCompatActivity {


   private ListView lv;
   private ArrayList<String> printers_location;
   private String[] printers;
   private String[] printers_loc;
   private List<String> printer_ID;
   private List<String> printer_location;
   private ArrayAdapter<String> adapter;
   private FirebaseAuth firebaseAuth;
   private DatabaseReference db_reference;
   private ImageButton ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_printout_online_printers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();




        lv = (ListView)findViewById(R.id.printers);
        ref = (ImageButton) findViewById(R.id.refresh);
        // Array holding our data
        printers = new String[] {};
        printers_loc = new String[] {};
        printer_ID = new ArrayList<String>(Arrays.asList(printers));
        printer_location = new ArrayList<String>(Arrays.asList(printers_loc));
        adapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.listText, printer_location);

//        foody = {"H15 A wing", "H11", "H15 C wing", "H10 A wing", "H2"};
        //adapter which will convert each data item into view item.

        //place each view-item inside listview by setting adapter for our listview

        refresh_list ();


        ref.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            refresh_list();

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView listText = (TextView) view.findViewById(R.id.listText);
                String text = listText.getText().toString();
                int poss = search(text, printer_location);
                Log.e("pos",printer_ID.get(poss-1));
                Toast.makeText(take_printout_online_printers.this, text, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(take_printout_online_printers.this, document_upload.class);
                intent.putExtra("selected-item", text);
                intent.putExtra("shop_ID",printer_ID.get(poss-1));
                startActivity(intent);
                finish();
            }
        });
    }

    public void refresh_list ()
    {


        db_reference.child("Online_printers").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        printer_ID.clear();
                        printer_location.clear();
                        for(DataSnapshot d_snapshot : dataSnapshot.getChildren()) {

                            printer_ID.add(d_snapshot.getKey().toString());
                            printer_location.add(d_snapshot.getValue().toString());
                            Log.e("ID", d_snapshot.getKey().toString());
                            Log.e("ID", d_snapshot.getValue().toString());
                        }
                        lv.setAdapter(adapter);




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    public int search(String searchStr, List<String> aList)
    {

        boolean found = false;
        Iterator<String> iter = aList.iterator();
        String curItem="";
        int pos=0;

        while ( iter .hasNext() == true )
        {
            pos=pos+1;
            curItem =(String) iter .next();
            if (curItem.equals(searchStr)  ) {
                found = true;
                break;
            }

        }

        if ( found == false ) {
            pos=0;
        }

        if (pos!=0)
        {
//            System.out.println(searchStr + " City Found in position : " + pos);
            return pos;
        }
        else
        {

//            System.out.println(searchStr + " City not found");
            return pos;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
