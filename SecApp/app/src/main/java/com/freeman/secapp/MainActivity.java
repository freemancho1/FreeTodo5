package com.freeman.secapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.freeman.secapp.components.project.view.ProjectListActivity;
import com.freeman.secapp.components.project.view.ProjectMenuListActivity;
import com.freeman.secapp.test.multilevelview.MultiLevelActivity;
import com.freeman.secapp.utils.db.InitialDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddSchedule = findViewById(R.id.main_fab);
        fabAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.sys_msg_any_task, Toast.LENGTH_LONG).show();
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.main_activity);
        NavigationView navigationView = findViewById(R.id.main_nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_project_manager:
                Intent intent = new Intent(getApplicationContext(), ProjectListActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.main_activity);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.main_activity);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_option_init_data:
                Intent intent = new Intent(getApplicationContext(), InitialDatabase.class);
                startActivity(intent);
                break;
            case R.id.main_option_test_multiview:
                Intent intent1 = new Intent(getApplicationContext(), ProjectMenuListActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
