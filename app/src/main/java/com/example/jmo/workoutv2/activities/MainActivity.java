package com.example.jmo.workoutv2.activities;

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
import com.example.jmo.workoutv2.fragments.MyProgramsFragmentOLD;

public class MainActivity extends AppCompatActivity {

    private boolean isMainVisible;
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
            isMainVisible=true;
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
                        MyProgramsFragmentOLD myProgramsFragmentOLD = new MyProgramsFragmentOLD();
                        transaction.replace(R.id.fragment_container, myProgramsFragmentOLD).addToBackStack(null);
                        transaction.commit();
                        isMainVisible = false;
                        break;
                    case R.id.nav_Placeholder:
                        MyProgramsFragment placeholderFragment = new MyProgramsFragment();
                        transaction.replace(R.id.fragment_container, placeholderFragment).addToBackStack(null);
                        transaction.commit();
                        isMainVisible = false;
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nav_view = findViewById(R.id.nav_view);

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (isMainVisible) {
                    drawer_layout.openDrawer(GravityCompat.START);
                    return true;
                }
                else if (getSupportFragmentManager().getBackStackEntryCount() == 1 && isMainVisible == false) {
                    getSupportFragmentManager().popBackStack();
                    isMainVisible = true;
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_nav);
                    clearMenu(nav_view);
                }
                else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();;
                    clearMenu(nav_view);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_nav);
            clearMenu(nav_view);
            isMainVisible=true;
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            clearMenu(nav_view);
        }
    }

    private void clearMenu (NavigationView nav_view) {
        int size = nav_view.getMenu().size();
        for (int i = 0; i < size; i++) {
            nav_view.getMenu().getItem(i).setChecked(false);
        }
    }
}
