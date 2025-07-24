package com.png.wolibapracticaltask.data.model;

public class Interest {
    private String name;
    private boolean isSelected;

    public Interest(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}

