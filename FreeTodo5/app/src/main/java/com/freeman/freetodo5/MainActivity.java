package com.freeman.freetodo5;

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

import com.freeman.freetodo5.todolist.group.activity.TodoListGroupAdd;
import com.freeman.freetodo5.todolist.group.activity.TodoListGroupManager;
import com.freeman.freetodo5.todolist.group.adapter.TodoListGroupSideMenuFavoriteAdapter;
import com.freeman.freetodo5.todolist.group.adapter.TodoListGroupSideMenuItemsAdapter;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;
import com.freeman.freetodo5.utils.color.MyColorPicker;
import com.freeman.freetodo5.utils.db.InitDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "[MAIN]";

    private DrawerLayout mMainActivity;

    private TodoListGroupRepository mTodoListGroupRepo;

    private TodoListGroupSideMenuFavoriteAdapter mFavoriteAdapter;
    private TodoListGroupSideMenuItemsAdapter mItemsAdapter;

    private RecyclerView mFavoriteView, mGroupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mTodoListGroupRepo = new TodoListGroupRepository(getApplication());

        mMainActivity = findViewById(R.id.main_activity);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mMainActivity, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                setAdapterItems();
            }
        };
        mMainActivity.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fabMain = findViewById(R.id.main_fab);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InitDatabase.class);
                startActivity(intent);
            }
        });

        setSideMenu();
    }

    @Override
    public void onBackPressed() {
        if (mMainActivity.isDrawerOpen(GravityCompat.START)) {
            mMainActivity.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setSideMenu() {

        findViewById(R.id.main_sidemenu_menu_today).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_7_days).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_all_days).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_group_manager).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_settings).setOnClickListener(sideMenuClickListener);
        findViewById(R.id.main_sidemenu_menu_init_db).setOnClickListener(sideMenuClickListener);

        mFavoriteAdapter = new TodoListGroupSideMenuFavoriteAdapter(this, mTodoListGroupRepo);
        mFavoriteView = findViewById(R.id.main_sidemenu_menu_list_favorite);
        mFavoriteView.setHasFixedSize(true);
        mFavoriteView.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteAdapter.setItemLists(mTodoListGroupRepo.getFavorites());
        mFavoriteView.setAdapter(mFavoriteAdapter);

        mItemsAdapter = new TodoListGroupSideMenuItemsAdapter(this, mTodoListGroupRepo);
        mGroupView = findViewById(R.id.main_sidemenu_menu_list_recycler_view);
        mGroupView.setHasFixedSize(false);
        mGroupView.setLayoutManager(new LinearLayoutManager(this));
        mItemsAdapter.setItemLists(mTodoListGroupRepo.getChildren(""));
        mGroupView.setAdapter(mItemsAdapter);

    }

    private void setAdapterItems() {

        if (GlobalVariable.getInstance().isSideMenuFavoriteChange()) {
            GlobalVariable.getInstance().setSideMenuFavoriteChange(false);
            List<TodoListGroup> favoriteItems = mTodoListGroupRepo.getFavorites();
            mFavoriteAdapter.setItemLists(favoriteItems);
            if (favoriteItems.size() > 0) {
                mFavoriteView.setVisibility(View.VISIBLE);
                findViewById(R.id.main_sidemenu_divider1).setVisibility(View.VISIBLE);
            } else {
                mFavoriteView.setVisibility(View.GONE);
                findViewById(R.id.main_sidemenu_divider1).setVisibility(View.GONE);
            }
        }

        if (GlobalVariable.getInstance().isSideMenuItemsChange()) {
            GlobalVariable.getInstance().setSideMenuItemsChange(false);
            List<TodoListGroup> itemsItems = mTodoListGroupRepo.getChildren("");
            mItemsAdapter.setItemLists(itemsItems);
            if (itemsItems.size() > 0) {
                mGroupView.setVisibility(View.VISIBLE);
                findViewById(R.id.main_sidemenu_divider2).setVisibility(View.VISIBLE);
            } else {
                mGroupView.setVisibility(View.GONE);
                findViewById(R.id.main_sidemenu_divider2).setVisibility(View.GONE);
            }
        }
    }

    View.OnClickListener sideMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Class intentClass = null;

            switch (v.getId()) {
                case R.id.main_sidemenu_menu_today:
                    intentClass = TodoListGroupAdd.class;
                    break;
                case R.id.main_sidemenu_menu_init_db:
                    intentClass = InitDatabase.class;
                    break;
                case R.id.main_sidemenu_menu_settings:
                    intentClass = MyColorPicker.class;
                    break;
                case R.id.main_sidemenu_menu_group_manager:
                    intentClass = TodoListGroupManager.class;
                    Log.d(LOG_TAG, "main_sidemenu_menu_group_manager click");
                    break;
                default:
                    break;
            }

            mMainActivity.closeDrawer(GravityCompat.START);

            if (intentClass != null) {
                Intent intent = new Intent(getApplicationContext(), intentClass);
                startActivity(intent);
            }
        }
    };

}
