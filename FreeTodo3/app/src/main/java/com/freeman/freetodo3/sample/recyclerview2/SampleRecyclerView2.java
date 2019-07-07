package com.freeman.freetodo3.sample.recyclerview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freeman.freetodo3.R;
import com.freeman.freetodo3.todo.group.model.TodoGroup;
import com.freeman.freetodo3.todo.group.model.TodoGroupRepository;

import java.util.ArrayList;
import java.util.List;

public class SampleRecyclerView2 extends AppCompatActivity {

    private TodoGroupRepository mTodoGroupRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_recycler_view2);

        setTitle(R.string.todogroup_manager_title);

        mTodoGroupRepo = new TodoGroupRepository(getApplication());
//        List<TodoGroup> groups = makeTodoGroups(0L);

        RecyclerView recyclerView = findViewById(R.id.sample_recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SampleRecyclerView2Adapter adapter = new SampleRecyclerView2Adapter(this, groups, mTodoGroupRepo);
        SampleRecyclerView2Adapter adapter = new SampleRecyclerView2Adapter(this, mTodoGroupRepo);
        recyclerView.setAdapter(adapter);
    }

    private List<TodoGroup> makeTodoGroups(Long parentId) {
        return mTodoGroupRepo.getChildren(parentId);
    }

    private List<TodoGroup> makeTodoGroups2(Long parentId) {
        List<TodoGroup> returnGroups = new ArrayList<>();

        List<TodoGroup> tempGroups = mTodoGroupRepo.getChildren(parentId);

        for (TodoGroup group : tempGroups) {
            returnGroups.add(group);
            if (group.isChildren()) {
                returnGroups.addAll(makeTodoGroups(group.getId()));
            }
        }

        return returnGroups;
    }
}
