package com.freeman.freetodo3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.freeman.freetodo3.sample.recyclerview.SampleRecyclerView;
import com.freeman.freetodo3.sample.recyclerview2.SampleRecyclerView2;
import com.freeman.freetodo3.todo.group.view.TodoGroupManager;
import com.freeman.freetodo3.utils.db.InitializationDatabase;
import com.freeman.freetodo3.utils.db.TestDatabase;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = "[MAIN]";

    private DrawerLayout mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        // android.support.v7.widget.Toolbar supported.
        setSupportActionBar(toolbar);

        FloatingActionButton fabMain = findViewById(R.id.main_fab);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), R.string.sys_msg_any_task, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SampleRecyclerView.class);
                startActivity(intent);
            }
        });

        mMainActivity = findViewById(R.id.main_activity);
        NavigationView navigationView = findViewById(R.id.sidemenu_show);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mMainActivity, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close
        );
        mMainActivity.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Button btnTest = findViewById(R.id.main_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestDatabase(getApplication()).TestTodoGroup();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Class intentClass = null;

        switch (menuItem.getItemId()) {
            case R.id.sidemenu_menu_todogroup_manager:
                intentClass = TodoGroupManager.class;
                break;
            case R.id.sidemenu_menu_todogroup_manager2:
                intentClass = SampleRecyclerView2.class;
                break;
            case R.id.sidemenu_menu_main2:
                intentClass = Main2Activity.class;
            default:
                break;
        }

        if (intentClass != null) {
            Intent intent = new Intent(getApplicationContext(), intentClass);
            startActivity(intent);
        }

        mMainActivity.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity.isDrawerOpen(GravityCompat.START)) {
            mMainActivity.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class intentClass = null;

        switch (item.getItemId()) {
            case R.id.main_optionmenu_init_data:
                intentClass = InitializationDatabase.class;
                break;
            default:
                break;
        }

        if (intentClass != null) {
            Intent intent = new Intent(getApplicationContext(), intentClass);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
