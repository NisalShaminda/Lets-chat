package com.example.subhashana.letschat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    //private Toolbar mToolbar;

    private FirebaseAuth mAuth;

    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsPagerAdapter myTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        //Tabs for MainActivity
        myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsPagerAdapter);
        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);


//        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("LetsChat");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
           LogOutUser();
        }
    }

    private void LogOutUser() {

        Intent startPageIntent = new Intent(Main2Activity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_button){

            mAuth.signOut();
            LogOutUser();
        }
        if(item.getItemId() == R.id.main_account_settings_button)
        {
            Intent settingsIntent = new Intent(Main2Activity.this, SettingActivity.class);
            startActivity(settingsIntent);
        }

        if(item.getItemId() == R.id.main_all_users_button)
        {
            Intent allUsersIntent = new Intent(Main2Activity.this, AllUsersActivity.class);
            startActivity(allUsersIntent);
        }
        return true;
    }
}
