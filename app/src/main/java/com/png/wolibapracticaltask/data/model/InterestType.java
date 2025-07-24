package com.png.wolibapracticaltask.data.model;

import java.util.ArrayList;

public class InterestType {
    private String categoryName;
    private ArrayList<Interest> interests;
    private boolean isExpanded;

    public InterestType(String categoryName, ArrayList<Interest> interests) {
        this.categoryName = categoryName;
        this.interests = interests;
        this.isExpanded = false;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Interest> getInterests() {
        return interests;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}

