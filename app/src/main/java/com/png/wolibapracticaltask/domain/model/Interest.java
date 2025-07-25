package com.png.wolibapracticaltask.domain.model;

public class Interest {
    public int id;
    public String name;
    public String interest_color_icon;
    public String interest_white_icon;
    public boolean isSelected;

    public Interest(int id, String name, String interest_color_icon, String interest_white_icon) {
        this.id = id;
        this.name = name;
        this.interest_color_icon = interest_color_icon;
        this.interest_white_icon = interest_white_icon;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getInterest_color_icon() {
        return interest_color_icon;
    }

    public String getInterest_white_icon() {
        return interest_white_icon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}

