package com.freeman.freetodo5.utils.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmInitDatabase extends AppCompatActivity {
    private static final String LOG_TAG = "[INIT DB]";
    
    private Realm mRealm;
    private TodoListGroupRepository mTodoListGroupRepo;
    private TextView mTodoListGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_init_database_activity);
        
        mRealm = Realm.getDefaultInstance();

        todoListGroupManager();

    }

    private void todoListGroupManager() {
        mTodoListGroupRepo = new TodoListGroupRepository(mRealm);

        mTodoListGroupList = findViewById(R.id.realm_todogroup_list);

        Button btnTodoListGroupInit = findViewById(R.id.realm_init_todogroup_button);
        btnTodoListGroupInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTodoListGroup();
                GlobalVariable.getInstance().setSideMenuFavoriteChange(true);
                GlobalVariable.getInstance().setSideMenuItemsChange(true);
            }
        });

        Button btnTodoListGroupRemoveAll = findViewById(R.id.realm_removeall_todogroup_button);
        btnTodoListGroupRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoListGroupRepo.removeAll();
                mTodoListGroupList.setText("Dataset not found.");
                GlobalVariable.getInstance().setSideMenuFavoriteChange(true);
                GlobalVariable.getInstance().setSideMenuItemsChange(true);
            }
        });

        Button btnGetAll = findViewById(R.id.realm_getall_button);
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTodoListGroup();
            }
        });
    }

    private List<TodoListGroup> getTreeTodoListGroup(String parentId) {
        List<TodoListGroup> TodoListGroups = new RealmList<>();
        List<TodoListGroup> results = mTodoListGroupRepo.getChildren(parentId);
        Log.d(LOG_TAG, "results size - " + results.size());
        for (TodoListGroup TodoListGroup: results) {
            TodoListGroups.add(TodoListGroup);
            if (TodoListGroup.isChildren()) {
                TodoListGroups.addAll(getTreeTodoListGroup(TodoListGroup.getId()));
            }
        }
        return TodoListGroups;
    }

    private void initTodoListGroup() {
        mTodoListGroupRepo.removeAll();

        List<TodoListGroup> todoGroups = new ArrayList<>();

        String parentId = "";
        int depth = 0;
        todoGroups.add(new TodoListGroup("팀업무", "팀과 관련된 업무 처리", 0xFF777777, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("연구개발", "연구개발과 관련된 업무 처리", 0xFF558888, parentId, depth, 2, true));
        todoGroups.add(new TodoListGroup("회사업무", "팀 이외에 회사 전체와 관련된 업무 처리", 0xFF4444EE, parentId, depth, 3, false));
        todoGroups.add(new TodoListGroup("사업지원", "사업부서 사업화 지원 업무 처리", 0xFF44DD44, parentId, depth, 4, false));

        parentId = todoGroups.get(0).getId();
        depth = todoGroups.get(0).getDepth() + 1;
        todoGroups.get(0).setChildren(true);
        todoGroups.add(new TodoListGroup("주무", "", 0xff999999, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("예산담당", "", 0xff999999, parentId, depth, 2, false));
        todoGroups.add(new TodoListGroup("품질담당", "", 0xff999999, parentId, depth, 3, false));
        todoGroups.add(new TodoListGroup("보안담당", "", 0xff999999, parentId, depth, 4, false));

        parentId = todoGroups.get(1).getId();
        depth = todoGroups.get(1).getDepth() + 1;
        todoGroups.get(1).setChildren(true);
        todoGroups.add(new TodoListGroup("인공지능 기반 보안관제 솔루션 개발", "", 0xff55aaaa, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("AI 챗봇 서비스 개발", "", 0xff55aaaa, parentId, depth, 2, false));
        todoGroups.add(new TodoListGroup("지하시설물 관리를 위한 AR/VR 솔루션 개발", "", 0xff55aaaa, parentId, depth, 3, false));

        parentId = todoGroups.get(3).getId();
        depth = todoGroups.get(3).getDepth() + 1;
        todoGroups.get(3).setChildren(true);
        todoGroups.add(new TodoListGroup("남동발전 전자결재 구축 사업", "", 0xFF55AAAA, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("MDMS 구축 사업", "", 0xFF55AAAA, parentId, depth, 2, false));

        parentId = todoGroups.get(8).getId();
        depth = todoGroups.get(8).getDepth() + 1;
        todoGroups.get(8).setChildren(true);
        todoGroups.add(new TodoListGroup("연구과제계획서 작성", "", 0xFFaaaaaa, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("연구수행계획서 작성", "", 0xFFaaaaaa, parentId, depth, 2, true));
        todoGroups.add(new TodoListGroup("분석단계 수행", "", 0xFFaaaaaa, parentId, depth, 3, false));

        parentId = todoGroups.get(9).getId();
        depth = todoGroups.get(9).getDepth() + 1;
        todoGroups.get(9).setChildren(true);
        todoGroups.add(new TodoListGroup("연구과제계획서 작성", "", 0xFFaaaaaa, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("연구수행계획서 작성", "", 0xFFaaaaaa, parentId, depth, 2, true));

        parentId = todoGroups.get(15).getId();
        depth = todoGroups.get(15).getDepth() + 1;
        todoGroups.get(15).setChildren(true);
        todoGroups.add(new TodoListGroup("SW영향평가 시행", "", 0xFFaaaaaa, parentId, depth, 1, false));
        todoGroups.add(new TodoListGroup("용역기간 적정성 평가", "", 0xFFaaaaaa, parentId, depth, 2, true));
        todoGroups.add(new TodoListGroup("용역 착수", "", 0xFFaaaaaa, parentId, depth, 3, false));

        mTodoListGroupRepo.insert(todoGroups);

        displayTodoListGroup();
    }

    private void displayTodoListGroup() {
        List<TodoListGroup> todoListGroups = getTreeTodoListGroup("");
        mTodoListGroupList.setText("");
        String space = "";
        if (todoListGroups.size() > 0) {
            for (TodoListGroup todoGroup : todoListGroups) {
                space = "";
                for (int i=0; i<todoGroup.getDepth(); i++) space += "      ";
                mTodoListGroupList.append(space + todoGroup.getName() + "\n");
            }
        } else {
            mTodoListGroupList.setText("Dataset not found.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }
}
