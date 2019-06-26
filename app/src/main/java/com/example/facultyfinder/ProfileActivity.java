package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    TextView textViewusername;
    TextView textVieworganisation;
    TextView textViewemail;
    TextView textViewphonenumber;
    TextView textViewnooffaculty;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setfront();
        Toolbar toolbar = findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        NavigationView navigationView = findViewById(R.id.nav_viewprofile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void setfront()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        textViewusername = (TextView)findViewById(R.id.username);
        textVieworganisation=(TextView)findViewById(R.id.organisation);
        textViewemail=(TextView)findViewById(R.id.email);
        textViewphonenumber=(TextView)findViewById(R.id.phonenumber);
        textViewnooffaculty=(TextView)findViewById(R.id.nooffaculty);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileInfo profileInfo=dataSnapshot.getValue(ProfileInfo.class);
                dochange(profileInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void dochange(ProfileInfo profileInfo)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        Log.w("thala1","cool");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewemail.setText(user.getEmail());
        Log.w("thala2","cool");
        textViewusername.setText(profileInfo.getUsername());
        textVieworganisation.setText(profileInfo.getOrganisationname());
       // textViewphonenumber.setText(profileInfo.getPhonenumber());
        textViewnooffaculty.setText("0");
    }
    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(ProfileActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            // Handle the camera action
        } else if (id == R.id.nav_facultylist) {

        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(ProfileActivity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {

        } else if (id == R.id.nav_addfaculty) {
            Intent QueryIntent= new Intent(ProfileActivity.this,AddFacultyActivity.class);
            startActivity(QueryIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}