package com.example.lkh.printe;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driver_activity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activity);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            db_reference = FirebaseDatabase.getInstance().getReference();
            String id1 = firebaseAuth.getCurrentUser().getUid();

            db_reference.child(id1).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            save_user_information user = dataSnapshot.getValue(save_user_information.class);


                            Intent intent = new Intent(Driver_activity.this, Home.class);
                            intent.putExtra("mail", user.email);
                            intent.putExtra("name", user.name);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Driver_activity.this, "No Connection",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
        {
            Intent intent = new Intent(Driver_activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
