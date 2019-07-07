package com.freeman.secapp.components.project.model;

import com.multilevelview.models.RecyclerViewItem;

public class ProjectItem extends RecyclerViewItem {
    private Project projectItem;

    public ProjectItem(int level) {
        super(level);
    }

    public Project getProjectItem() {
        return this.projectItem;
    }

    public void setProjectItem(Project projectItem) {
        this.projectItem = projectItem;
    }
}
