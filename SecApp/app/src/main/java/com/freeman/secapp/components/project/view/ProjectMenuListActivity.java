package com.freeman.secapp.components.project.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.freeman.secapp.R;
import com.freeman.secapp.components.project.adapter.ProjectMenuListAdapter;
import com.freeman.secapp.components.project.model.Project;
import com.freeman.secapp.components.project.model.ProjectItem;
import com.freeman.secapp.components.project.model.ProjectViewModel;
import com.multilevelview.MultiLevelRecyclerView;
import com.multilevelview.models.RecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class ProjectMenuListActivity extends AppCompatActivity {

    private ProjectViewModel mProjectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu_list);
        setTitle(R.string.project_list_title);

        mProjectModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

        final MultiLevelRecyclerView mContentView =
                findViewById(R.id.project_menu_list_recyclerview);
        mContentView.setLayoutManager(new LinearLayoutManager(this));
        List<ProjectItem> items = (List<ProjectItem>) makeViewData(0);

        ProjectMenuListAdapter adapter = new ProjectMenuListAdapter(this, items, mContentView);
        mContentView.setAdapter(adapter);
        mContentView.setToggleItemOnClick(true);
        mContentView.setAccordion(false);
        mContentView.openTill(1,2);
    }

    private List<?> makeViewData(int parentId) {
        List<RecyclerViewItem> items = new ArrayList<>();

        List<Project> menuItems = mProjectModel.getAllChildren(parentId);
        for (Project project: menuItems) {
            ProjectItem item = new ProjectItem(project.getDepth());
            item.setProjectItem(project);
            if (project.isChildren()) {
                item.addChildren(
                        (List<RecyclerViewItem>) makeViewData(project.getId())
                );
            }
            items.add(item);
        }

        return items;
    }
}
