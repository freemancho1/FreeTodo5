package com.freeman.freetodo5.utils.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freeman.freetodo5.GlobalVariable;
import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;
import com.freeman.freetodo5.utils.color.adapter.ColorInitDbAdapter;
import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

import java.util.ArrayList;
import java.util.List;

public class InitDatabase extends AppCompatActivity {

    private TodoListGroupRepository mTodoListGroupRepo;
    private TextView mTodoListGroupList;

    private ColorRepository mColorRepo;
    private RecyclerView mColorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_database_activity);
        setTitle(R.string.init_db_title);

        mTodoListGroupRepo = new TodoListGroupRepository(getApplication());
        mTodoListGroupList = findViewById(R.id.init_todogroup_list);

        mColorRepo = new ColorRepository(getApplication());
        mColorLayout = findViewById(R.id.init_color_list);

        Button btnAllInit = findViewById(R.id.init_all_database);
        btnAllInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTodoListGroup();
                displayTodoListGroup();
                initColor();
            }
        });

        todoListGroupManager();
        colorManager();
    }

    private void todoListGroupManager() {

        Button btnTodoListGroupInit = findViewById(R.id.init_init_todogroup_button);
        btnTodoListGroupInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTodoListGroup();
                displayTodoListGroup();
                GlobalVariable.getInstance().setSideMenuChange(true);
            }
        });

        Button btnTodoListGroupDeleteAll = findViewById(R.id.init_removeall_todogroup_button);
        btnTodoListGroupDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoListGroupRepo.removeAll();
                displayTodoListGroup();
                GlobalVariable.getInstance().setSideMenuChange(true);
            }
        });

        Button btnTodoListGroupGetAll = findViewById(R.id.init_getall_todogroup_button);
        btnTodoListGroupGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTodoListGroup();
            }
        });

    }

    private void initTodoListGroup() {
        mTodoListGroupRepo.removeAll();

        List<TodoListGroup> todoGroups = new ArrayList<>();

        String parentId = "";
        int depth = 0;
        todoGroups.add(new TodoListGroup("팀업무", "팀과 관련된 업무 처리", 0xFF0D6F3E, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("연구개발", "연구개발과 관련된 업무 처리", 0xFFEF4C51, parentId, depth, 2, 1));
        todoGroups.add(new TodoListGroup("회사업무", "팀 이외에 회사 전체와 관련된 업무 처리", 0xFF214196, parentId, depth, 3, 0));
        todoGroups.add(new TodoListGroup("사업지원", "사업부서 사업화 지원 업무 처리", 0xFF23B375, parentId, depth, 4, 0));

        parentId = todoGroups.get(0).getId();
        depth = todoGroups.get(0).getDepth() + 1;
        todoGroups.get(0).setChildren(true);
        todoGroups.add(new TodoListGroup("주무", "", 0xFF447436, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("예산담당", "", 0xFF447436, parentId, depth, 2, 0));
        todoGroups.add(new TodoListGroup("품질담당", "", 0xFF447436, parentId, depth, 3, 0));
        todoGroups.add(new TodoListGroup("보안담당", "", 0xFF447436, parentId, depth, 4, 0));

        parentId = todoGroups.get(1).getId();
        depth = todoGroups.get(1).getDepth() + 1;
        todoGroups.get(1).setChildren(true);
        todoGroups.add(new TodoListGroup("인공지능 기반 보안관제 솔루션 개발", "", 0xFFF2715C, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("AI 챗봇 서비스 개발", "", 0xFFF2715C, parentId, depth, 2, 0));
        todoGroups.add(new TodoListGroup("지하시설물 관리를 위한 AR/VR 솔루션 개발", "", 0xFFF2715C, parentId, depth, 3, 0));

        parentId = todoGroups.get(3).getId();
        depth = todoGroups.get(3).getDepth() + 1;
        todoGroups.get(3).setChildren(true);
        todoGroups.add(new TodoListGroup("남동발전 전자결재 구축 사업", "", 0xFF1DAF88, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("MDMS 구축 사업", "", 0xFF1DAF88, parentId, depth, 2, 0));

        parentId = todoGroups.get(8).getId();
        depth = todoGroups.get(8).getDepth() + 1;
        todoGroups.get(8).setChildren(true);
        todoGroups.add(new TodoListGroup("연구과제계획서 작성", "", 0xFFF47D55, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("연구수행계획서 작성", "", 0xFFF47D55, parentId, depth, 2, 1));
        todoGroups.add(new TodoListGroup("분석단계 수행", "", 0xFFF47D55, parentId, depth, 3, 0));

        parentId = todoGroups.get(9).getId();
        depth = todoGroups.get(9).getDepth() + 1;
        todoGroups.get(9).setChildren(true);
        todoGroups.add(new TodoListGroup("연구과제계획서 작성", "", 0xFFF47D55, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("연구수행계획서 작성", "", 0xFFF47D55, parentId, depth, 2, 1));

        parentId = todoGroups.get(15).getId();
        depth = todoGroups.get(15).getDepth() + 1;
        todoGroups.get(15).setChildren(true);
        todoGroups.add(new TodoListGroup("SW영향평가 시행", "", 0xFFF3954D, parentId, depth, 1, 0));
        todoGroups.add(new TodoListGroup("용역기간 적정성 평가", "", 0xFFF3954D, parentId, depth, 2, 1));
        todoGroups.add(new TodoListGroup("용역 착수", "", 0xFFF3954D, parentId, depth, 3, 0));

        mTodoListGroupRepo.insert(todoGroups);
    }

    private void displayTodoListGroup() {
        List<TodoListGroup> todoListGroups = mTodoListGroupRepo.getTree("");

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


    private void colorManager() {

        Button btnColorInit = findViewById(R.id.init_init_color_button);
        btnColorInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColor();
                displayColor();
            }
        });

    }
    private void initColor() {
        mColorRepo.removeAll();

        List<Color> colors = new ArrayList<>();

        colors.add(new Color(0xFF0D6F3E, "평화", true));
        colors.add(new Color(0xFF447436, "조화", true));
        colors.add(new Color(0xFF088950, "안도", true));
        colors.add(new Color(0xFF1EA563, "자립", true));
        colors.add(new Color(0xFF61A745, "의지", true));

        colors.add(new Color(0xFF23B375, "평온", true));
        colors.add(new Color(0xFF1DAF88, "안정", true));
        colors.add(new Color(0xFF1AAD9D, "희망", true));
        colors.add(new Color(0xFF0BB7B9, "도전", true));
        colors.add(new Color(0xFF17B6CC, "미래", true));

        colors.add(new Color(0xFFEC1E4C, "황홀", true));
        colors.add(new Color(0xFFEC202C, "정열", true));
        colors.add(new Color(0xFFF04C53, "환희", true));
        colors.add(new Color(0xFFF25F7C, "행복", true));
        colors.add(new Color(0xFFF37670, "안락", true));

        colors.add(new Color(0xFFEF4C51, "감격", true));
        colors.add(new Color(0xFFF2715C, "희열", true));
        colors.add(new Color(0xFFF47D55, "감동", true));
        colors.add(new Color(0xFFF3954D, "내일", true));
        colors.add(new Color(0xFFF5BD6A, "아이", true));

        colors.add(new Color(0xFF675FAA, "공존", true));
        colors.add(new Color(0xFF774899, "소통", true));
        colors.add(new Color(0xFF9355A0, "화합", true));
        colors.add(new Color(0xFFA7589A, "교감", true));
        colors.add(new Color(0xFFCC58A1, "나눔", true));

        colors.add(new Color(0xFF214196, "여유", true));
        colors.add(new Color(0xFF1E68B3, "휴식", true));
        colors.add(new Color(0xFF33A4DC, "자유", true));
        colors.add(new Color(0xFF1DBDEF, "하늘", true));
        colors.add(new Color(0xFFBBE5F5, "구름", true));

        mColorRepo.insert(colors);
    }

    private void displayColor() {
        ColorInitDbAdapter adapter = new ColorInitDbAdapter(this, mColorRepo);
        mColorLayout.setHasFixedSize(true);
        mColorLayout.setLayoutManager(new GridLayoutManager(this, 5));
        adapter.setItemLists(mColorRepo.getAll());
        mColorLayout.setAdapter(adapter);
    }
}