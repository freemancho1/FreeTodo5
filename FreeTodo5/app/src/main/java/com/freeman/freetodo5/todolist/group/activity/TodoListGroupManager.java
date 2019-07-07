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
import com.freeman.freetodo5.utils.db.RealmInitDatabase;

import io.realm.Realm;

public class TodoListGroupManager extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_group_manager_activity);
        setTitle(R.string.sidemenu_menu_project);

        mRealm = Realm.getDefaultInstance();
        TodoListGroupRepository repository = new TodoListGroupRepository(mRealm);

        RecyclerView recyclerView = findViewById(R.id.todo_list_group_manager_view);
        TodoListGroupManagerAdapter adapter = new TodoListGroupManagerAdapter(this, repository);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemLists(repository.getAllTree(""));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabMain = findViewById(R.id.main_fab);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RealmInitDatabase.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mRealm != null) mRealm.close();
        super.onDestroy();
    }
}
