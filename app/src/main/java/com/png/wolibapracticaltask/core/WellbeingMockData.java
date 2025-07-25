package com.png.wolibapracticaltask.core;

import com.png.wolibapracticaltask.data.model.response.WellbeingItem;

import java.util.ArrayList;

public class WellbeingMockData {
    public static ArrayList<WellbeingItem> getWellbeingMockData() {
        ArrayList<WellbeingItem> mockData = new ArrayList<>();
        mockData.add(new WellbeingItem(1, "Physical Wellbeing", "Energy, movement, sleep, and routine care", 1));
        mockData.add(new WellbeingItem(2, "Mental Wellbeing", "Clarity, focus, and mindfulness", 1));
        mockData.add(new WellbeingItem(3, "Emotional Wellbeing", "Resilience, self-awareness, and stress regulation", 1));
        mockData.add(new WellbeingItem(4, "Social Wellbeing", "Relationships and meaningful connection", 1));
        mockData.add(new WellbeingItem(5, "Intellectual Wellbeing", "Growth, curiosity, and learning", 1));
        mockData.add(new WellbeingItem(6, "Occupational Wellbeing", "Purpose, performance, and work-life balance", 1));
        mockData.add(new WellbeingItem(7, "Spiritual Wellbeing", "Values, meaning, and inner alignment", 1));
        mockData.add(new WellbeingItem(8, "Environmental Wellbeing", "Healthy, safe, and productive surroundings", 1));
        mockData.add(new WellbeingItem(9, "Purpose & Contribution", "Giving back and living with meaning", 1));
        mockData.add(new WellbeingItem(10, "Longevity", "A sustainable, healthy lifestyle for the long term", 1));
        mockData.add(new WellbeingItem(11, "Nutritional Wellbeing", "Fueling your body and brain with intention", 1));
        mockData.add(new WellbeingItem(12, "Financial Wellbeing", "Security, budgeting, and long-term stability", 1));
        return mockData;
    }
}
