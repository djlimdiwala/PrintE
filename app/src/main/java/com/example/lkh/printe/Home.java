package com.example.lkh.printe;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkh.printe.Common.Common;
import com.example.lkh.printe.Remote.APIService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;
    boolean doubleBackToExitPressedOnce = false;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        db_reference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        Update_token(FirebaseInstanceId.getInstance().getToken());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView user_nm = (TextView) header.findViewById(R.id.user_name);
        TextView user_ml = (TextView) header.findViewById(R.id.user_mail);
        String temp = getIntent().getExtras().getString("name");
        String name_string = "Hello " + temp;
        String mail_string = getIntent().getExtras().getString("mail");
        user_nm.setText(name_string);
        user_ml.setText(mail_string);
        Toast.makeText(Home.this, "Welcome " + temp,
                Toast.LENGTH_LONG).show();



        ImageView record = (ImageView) findViewById(R.id.job_plus);
        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, take_printout_online_printers.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {


            String id1 = firebaseAuth.getCurrentUser().getUid();
            db_reference.child("users").child(id1).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            save_user_information user = dataSnapshot.getValue(save_user_information.class);


                            Intent intent = new Intent(Home.this, Edit_profile.class);
                            intent.putExtra("name",user.name);
                            intent.putExtra("mail", user.email);
                            intent.putExtra("mobile", user.mobile);
                            intent.putExtra("hostel",user.h_no);
                            intent.putExtra("room",user.r_no);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Home.this, "Connection lost...Try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

            // Handle the camera action
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {

            firebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(Home.this, "Successfully Logged out...",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    public void Update_token (String token)
    {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Tokens");
        Token data=new Token(token,false);
        tokens.child(firebaseAuth.getCurrentUser().getUid()).setValue(data);
    }



}
