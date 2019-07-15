package com.freeman.freetodo6;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.utils.color.model.Color;
import com.freeman.freetodo6.utils.color.model.ColorRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mMainActivityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



        //initDatabase();
    }

    public void initDatabase() {
        TodoGroupRepository todoGroupRepository = new TodoGroupRepository(getApplication());
        ColorRepository colorRepository = new ColorRepository(getApplication());

        todoGroupRepository.removeAll();

        List<TodoGroup> todoGroups = new ArrayList<>();

        String parentId = "";
        int depth = 0;
        todoGroups.add(new TodoGroup("팀업무", "팀과 관련된 업무 처리", 0xFF0D6F3E, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("연구개발", "연구개발과 관련된 업무 처리", 0xFFEF4C51, parentId, depth, 2, 1));
        todoGroups.add(new TodoGroup("회사업무", "팀 이외에 회사 전체와 관련된 업무 처리", 0xFF214196, parentId, depth, 3, 0));
        todoGroups.add(new TodoGroup("사업지원", "사업부서 사업화 지원 업무 처리", 0xFF23B375, parentId, depth, 4, 0));

        parentId = todoGroups.get(0).getId();
        depth = todoGroups.get(0).getDepth() + 1;
        todoGroups.get(0).setChildren(true);
        todoGroups.add(new TodoGroup("주무", "", 0xFF447436, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("예산담당", "", 0xFF447436, parentId, depth, 2, 0));
        todoGroups.add(new TodoGroup("품질담당", "", 0xFF447436, parentId, depth, 3, 0));
        todoGroups.add(new TodoGroup("보안담당", "", 0xFF447436, parentId, depth, 4, 0));

        parentId = todoGroups.get(1).getId();
        depth = todoGroups.get(1).getDepth() + 1;
        todoGroups.get(1).setChildren(true);
        todoGroups.add(new TodoGroup("인공지능 기반 보안관제 솔루션 개발", "", 0xFFF2715C, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("AI 챗봇 서비스 개발", "", 0xFFF2715C, parentId, depth, 2, 0));
        todoGroups.add(new TodoGroup("지하시설물 관리를 위한 AR/VR 솔루션 개발", "", 0xFFF2715C, parentId, depth, 3, 0));

        parentId = todoGroups.get(3).getId();
        depth = todoGroups.get(3).getDepth() + 1;
        todoGroups.get(3).setChildren(true);
        todoGroups.add(new TodoGroup("남동발전 전자결재 구축 사업", "", 0xFF1DAF88, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("MDMS 구축 사업", "", 0xFF1DAF88, parentId, depth, 2, 0));

        parentId = todoGroups.get(8).getId();
        depth = todoGroups.get(8).getDepth() + 1;
        todoGroups.get(8).setChildren(true);
        todoGroups.add(new TodoGroup("연구과제계획서 작성", "", 0xFFF47D55, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("연구수행계획서 작성", "", 0xFFF47D55, parentId, depth, 2, 1));
        todoGroups.add(new TodoGroup("분석단계 수행", "", 0xFFF47D55, parentId, depth, 3, 0));

        parentId = todoGroups.get(9).getId();
        depth = todoGroups.get(9).getDepth() + 1;
        todoGroups.get(9).setChildren(true);
        todoGroups.add(new TodoGroup("연구과제계획서 작성", "", 0xFFF47D55, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("연구수행계획서 작성", "", 0xFFF47D55, parentId, depth, 2, 1));

        parentId = todoGroups.get(15).getId();
        depth = todoGroups.get(15).getDepth() + 1;
        todoGroups.get(15).setChildren(true);
        todoGroups.add(new TodoGroup("SW영향평가 시행", "", 0xFFF3954D, parentId, depth, 1, 0));
        todoGroups.add(new TodoGroup("용역기간 적정성 평가", "", 0xFFF3954D, parentId, depth, 2, 1));
        todoGroups.add(new TodoGroup("용역 착수", "", 0xFFF3954D, parentId, depth, 3, 0));

        todoGroupRepository.insert(todoGroups);

        List<TodoGroup> resultTodoGroups = todoGroupRepository.getTree("");
        String resultMessage = "";
        String space = "";

        if (resultTodoGroups.size() > 0) {
            for (TodoGroup todoGroup: resultTodoGroups) {
                space = "";
                for (int i=0; i<todoGroup.getDepth(); i++) space += "      ";
                resultMessage += space + todoGroup.getName() + "\n";
            }
        } else {
            resultMessage = "Dataset not found...";
        }

        Log.d(LOG_TAG, resultMessage);

        colorRepository.removeAll();

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

        colorRepository.insert(colors);

        List<Color> resultColors = colorRepository.getAll();
        for (Color color: resultColors) Log.d(LOG_TAG, color.toString());

    }
}
