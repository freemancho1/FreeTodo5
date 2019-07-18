package com.freeman.freetodo6.todo.group.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.todo.group.view.adapter.ManagerAdapter;
import com.freeman.freetodo6.utils.AppStatus;

public class ManagerActivity extends AppCompatActivity {

    private ManagerAdapter mAdapter;
    private TodoGroupRepository mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todogroup_manager_activity);
        setTitle(R.string.todogroup_manager_title);

        mRepo = new TodoGroupRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.todogroup_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ManagerAdapter(this, mRepo);
        mAdapter.setItemLists(mRepo.getTree(""));
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fabMain = findViewById(R.id.main_fab);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        if (AppStatus.getInstance().isMenuChange()) mAdapter.setItemLists(mRepo.getTree(""));
        super.onRestart();
    }
}
