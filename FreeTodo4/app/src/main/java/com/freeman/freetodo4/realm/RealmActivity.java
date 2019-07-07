package com.freeman.freetodo4.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freeman.freetodo4.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmActivity extends AppCompatActivity {
    private static final String LOG_TAG = "[REALM]";

    private Realm mRealm;
    private TodoGroupRepository mTodoGroupRepo;

    private TextView mTodoGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_activity);

        mRealm = Realm.getDefaultInstance();

        mTodoGroupRepo = new TodoGroupRepository(mRealm);

        Button btnTodoGroupInit = findViewById(R.id.realm_init_todogroup_button);
        btnTodoGroupInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTodoGroup();
            }
        });

        Button btnTodoGroupRemoveAll = findViewById(R.id.realm_removeall_todogroup_button);
        btnTodoGroupRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoGroupRepo.removeAll();
            }
        });

        mTodoGroupList = findViewById(R.id.realm_todogroup_list);
        RealmList<TodoGroup> todoGroups = getTree("");
        for (TodoGroup todoGroup: todoGroups) {
            mTodoGroupList.append(todoGroup.getName() + "\n");
        }

    }

    private RealmList<TodoGroup> getTree(String parentId) {
        RealmList<TodoGroup> todoGroups = new RealmList<>();
        RealmResults<TodoGroup> results = mTodoGroupRepo.getChildren(parentId);
        Log.d(LOG_TAG, "results size - " + results.size());
        for (TodoGroup todoGroup: results) {
            todoGroups.add(todoGroup);
            if (todoGroup.isChildren()) {
                todoGroups.addAll(getTree(todoGroup.getId()));
            }
        }
        return todoGroups;
    }


    private void initTodoGroup() {
        mTodoGroupRepo.removeAll();

        List<TodoGroup> todoGroups = new ArrayList<>();

        todoGroups.add(new TodoGroup("팀업무", "팀과 관련된 업무 처리", 0xFF777777, "", 0, 1, false));
        todoGroups.add(new TodoGroup("연구개발", "연구개발과 관련된 업무 처리", 0xFF558888, "", 0, 2, true));
        todoGroups.add(new TodoGroup("회사업무", "팀 이외에 회사 전체와 관련된 업무 처리", 0xFF4444EE, "", 0, 3, false));
        todoGroups.add(new TodoGroup("사업지원", "사업부서 사업화 지원 업무 처리", 0xFF44DD44, "", 0, 4, false));

        todoGroups.add(new TodoGroup("주무", "", 0xff999999, todoGroups.get(0).getId(), todoGroups.get(0).getDepth()+1, 1, false));
        todoGroups.add(new TodoGroup("예산담당", "", 0xff999999, todoGroups.get(0).getId(), todoGroups.get(0).getDepth()+1, 2, false));
        todoGroups.add(new TodoGroup("품질담당", "", 0xff999999, todoGroups.get(0).getId(), todoGroups.get(0).getDepth()+1, 3, false));
        todoGroups.add(new TodoGroup("보안담당", "", 0xff999999, todoGroups.get(0).getId(), todoGroups.get(0).getDepth()+1, 4, false));

        todoGroups.add(new TodoGroup("인공지능 기반 보안관제 솔루션 개발", "", 0xff55aaaa, todoGroups.get(1).getId(), todoGroups.get(1).getDepth()+1, 1, false));
        todoGroups.add(new TodoGroup("AI 챗봇 서비스 개발", "", 0xff55aaaa, todoGroups.get(1).getId(), todoGroups.get(1).getDepth()+1, 2, false));
        todoGroups.add(new TodoGroup("지하시설물 관리를 위한 AR/VR 솔루션 개발", "", 0xff55aaaa, todoGroups.get(1).getId(), todoGroups.get(1).getDepth()+1, 3, false));

        todoGroups.add(new TodoGroup("남동발전 전자결재 구축 사업","", 0xFF55AAAA, todoGroups.get(3).getId(), todoGroups.get(3).getDepth()+1, 1, false));
        todoGroups.add(new TodoGroup("MDMS 구축 사업","", 0xFF55AAAA, todoGroups.get(3).getId(), todoGroups.get(3).getDepth()+1, 2, false));

        todoGroups.add(new TodoGroup("연구과제계획서 작성","", 0xFFaaaaaa, todoGroups.get(8).getId(), todoGroups.get(8).getDepth()+1, 1, false));
        todoGroups.add(new TodoGroup("연구수행계획서 작성","", 0xFFaaaaaa, todoGroups.get(8).getId(), todoGroups.get(8).getDepth()+1, 2, true));
        todoGroups.add(new TodoGroup("분석단계 수행","", 0xFFaaaaaa, todoGroups.get(8).getId(), todoGroups.get(8).getDepth()+1, 3, false));

        todoGroups.get(0).setChildren(true);
        todoGroups.get(1).setChildren(true);
        todoGroups.get(3).setChildren(true);
        todoGroups.get(8).setChildren(true);

        mTodoGroupRepo.insert(todoGroups);

        RealmResults<TodoGroup> results = mTodoGroupRepo.get();
        Log.d(LOG_TAG, "results size - " + results.size());
        for (TodoGroup todoGroup: results) {
            Log.d(LOG_TAG, todoGroup.toString());
        }

    }


    @Override
    protected void onDestroy() {
        if (mRealm != null) mRealm.close();
        super.onDestroy();
    }
}
