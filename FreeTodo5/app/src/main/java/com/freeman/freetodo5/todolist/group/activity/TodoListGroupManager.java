package com.freeman.freetodo5.todolist.group.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.adapter.TodoListGroupManagerAdapter;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;
import com.freeman.freetodo5.utils.db.InitDatabase;

public class TodoListGroupManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_group_manager_activity);
        setTitle(R.string.sidemenu_menu_project);

        TodoListGroupRepository repository = new TodoListGroupRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.todo_list_group_manager_view);
        TodoListGroupManagerAdapter adapter = new TodoListGroupManagerAdapter(this, repository);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemLists(repository.getTree(""));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabMain = findViewById(R.id.main_fab);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InitDatabase.class);
                startActivity(intent);
            }
        });
    }

}
