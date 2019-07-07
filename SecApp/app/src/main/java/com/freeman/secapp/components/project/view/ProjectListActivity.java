package com.freeman.secapp.components.project.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.freeman.secapp.R;
import com.freeman.secapp.components.project.adapter.ProjectListAdapter;
import com.freeman.secapp.components.project.model.ProjectViewModel;

public class ProjectListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        setTitle(R.string.project_list_title);

        RecyclerView recyclerView = findViewById(R.id.project_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProjectViewModel model = ViewModelProviders.of(this).get(ProjectViewModel.class);
        ProjectListAdapter adapter = new ProjectListAdapter(this);

        recyclerView.setAdapter(adapter);
        adapter.setProjects(model.getAllChildren(0));

        FloatingActionButton fabAdd = findViewById(R.id.project_add_fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.project_option_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
