package com.png.wolibapracticaltask.data.model.response;

public class WellbeingItem {
    int id;
    String pillar_title;
    String description;
    int is_active;

    public WellbeingItem(int id, String pillar_title, String description, int is_active) {
        this.id = id;
        this.pillar_title = pillar_title;
        this.description = description;
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public String getPillar_title() {
        return pillar_title;
    }

    public String getDescription() {
        return description;
    }

    public int getIs_active() {
        return is_active;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
