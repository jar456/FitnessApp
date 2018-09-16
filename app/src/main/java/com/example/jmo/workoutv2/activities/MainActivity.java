package com.example.jmo.workoutv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.fragments.MainFragment;
import com.example.jmo.workoutv2.fragments.MyProgramsFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView nav_view;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
        }

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_nav);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

                int id = item.getItemId(); // gets Item id
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                item.setChecked(true); // set item as selected to be highlighted
                drawer_layout.closeDrawers(); // close drawer when item is tapped
                setTitle(item.getTitle());  // set action bar title
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back); // Change Action Home Button

                switch (item.getItemId()) {
                    case R.id.nav_MyPrograms:
                        MyProgramsFragment myProgramsFragment = new MyProgramsFragment();
                        transaction.replace(R.id.fragment_container, myProgramsFragment).addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.nav_ExerciseList:
                        Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        clearMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nav_view = findViewById(R.id.nav_view);

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    clearMenu();
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            clearMenu();
        }
    }

    private void clearMenu() {
        int size = nav_view.getMenu().size();
        for (int i = 0; i < size; i++) {
            nav_view.getMenu().getItem(i).setChecked(false);
        }
    }
}
