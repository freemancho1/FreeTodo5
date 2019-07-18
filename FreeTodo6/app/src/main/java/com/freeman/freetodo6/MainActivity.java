package com.freeman.freetodo6;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.todo.group.view.ManagerActivity;
import com.freeman.freetodo6.todo.group.view.adapter.SideMenuFavoriteAdapter;
import com.freeman.freetodo6.todo.group.view.adapter.SideMenuMenuAdapter;
import com.freeman.freetodo6.utils.AppStatus;
import com.freeman.freetodo6.utils.color.model.Color;
import com.freeman.freetodo6.utils.color.model.ColorRepository;
import com.freeman.freetodo6.utils.db.DatabaseInitialization;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mMainActivityLayout;

    private TodoGroupRepository mTodoGroupRepo;

    private SideMenuMenuAdapter mMenuAdapter;
    private SideMenuFavoriteAdapter mFavoriteAdapter;

    private RecyclerView mMenuView, mFavoriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mMainActivityLayout = findViewById(R.id.main_activity);

        mTodoGroupRepo = new TodoGroupRepository(getApplication());
        mMenuAdapter = new SideMenuMenuAdapter(this, mTodoGroupRepo);
        mFavoriteAdapter = new SideMenuFavoriteAdapter(this, mTodoGroupRepo);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mMainActivityLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                setAdapterItems();
            }
        };

        mMainActivityLayout.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fabThisActivity = findViewById(R.id.main_fab);
        fabThisActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setSideMenu();

    }


    private void setSideMenu() {

        mMenuAdapter.setItemLists(mTodoGroupRepo.getChildren(""));
        mMenuView = findViewById(R.id.main_sidemenu_menu_todogroups);
        mMenuView.setHasFixedSize(false);
        mMenuView.setLayoutManager(new LinearLayoutManager(this));
        mMenuView.setAdapter(mMenuAdapter);

        mFavoriteAdapter.setItemLists(mTodoGroupRepo.getFavorites());
        mFavoriteView = findViewById(R.id.main_sidemenu_menu_favorites);
        mFavoriteView.setHasFixedSize(true);
        mFavoriteView.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteView.setAdapter(mFavoriteAdapter);

        findViewById(R.id.main_sidemenu_menu_today).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_day_7).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_day_all).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_group_manager).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_settings).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_init_db).setOnClickListener(sideMenuClickListener);
    }

    private void setAdapterItems() {
        AppStatus status = AppStatus.getInstance();

        if (status.isFavoriteChange()) {
            status.setFavoriteChange(false);

            List<TodoGroup> favoriteItems = mTodoGroupRepo.getFavorites();
            View divider1 = findViewById(R.id.main_sidemenu_divider1);
            if (favoriteItems.size() > 0) {
                mFavoriteView.setVisibility(View.VISIBLE);
                divider1.setVisibility(View.VISIBLE);
            } else {
                mFavoriteView.setVisibility(View.GONE);
                divider1.setVisibility(View.GONE);
            }

            mFavoriteAdapter.setItemLists(favoriteItems);
        }

        if (status.isMenuChange()) {
            status.setMenuChange(false);

            List<TodoGroup> menuItems = mTodoGroupRepo.getChildren("");
            View divider2 = findViewById(R.id.main_sidemenu_divider2);
            if (menuItems.size() > 0) {
                mMenuView.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);
            } else {
                mMenuView.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
            }

            mMenuAdapter.setItemLists(menuItems);
        }
    }

    View.OnClickListener sideMenuClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Class intentClass = null;

            switch (v.getId()) {
                case R.id.main_sidemenu_menu_group_manager:
                    intentClass = ManagerActivity.class;
                    break;
                case R.id.main_sidemenu_menu_init_db:
                    intentClass = DatabaseInitialization.class;
                    break;
                default:
                    break;
            }

            mMainActivityLayout.closeDrawer(GravityCompat.START);

            if (intentClass != null) {
                Intent intent = new Intent(getApplicationContext(), intentClass);
                startActivity(intent);
            }
        }

    };

}
