package com.png.wolibapracticaltask.data.model.response;

public class WellbeingItem {
    int id;
    String pillar_title;
    String description;
    int is_active;
    Integer badgeNumber;
    private boolean isSelected;

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

    public boolean isSelected() {
        return isSelected;
    }

    public int getBadgeNumber() { return badgeNumber; }

    public void setBadgeNumber(Integer badgeNumber) { this.badgeNumber = badgeNumber; }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
