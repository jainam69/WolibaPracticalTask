package com.png.wolibapracticaltask.core;

import com.png.wolibapracticaltask.data.model.InterestType;
import com.png.wolibapracticaltask.data.model.Interest;

import java.util.ArrayList;
import java.util.Arrays;

public class InterestMockData {

    public static ArrayList<InterestType> getMockCategoryList() {
        ArrayList<InterestType> categories = new ArrayList<>();

        categories.add(new InterestType("Individual Sports", new ArrayList<>(Arrays.asList(
                new Interest("Aerobics"),
                new Interest("Ballet"),
                new Interest("Calisthenics"),
                new Interest("Dance"),
                new Interest("Gymnastics"),
                new Interest("Hiking"),
                new Interest("Obstacle Racing"),
                new Interest("Pilates"),
                new Interest("Running"),
                new Interest("Walking"),
                new Interest("Yoga")
        ))));

        categories.add(new InterestType("Ball Sports", new ArrayList<>(Arrays.asList(
                new Interest("Football"),
                new Interest("Basketball"),
                new Interest("Tennis"),
                new Interest("Volleyball")
        ))));

        categories.add(new InterestType("Wheel Sports", new ArrayList<>(Arrays.asList(
                new Interest("Cycling"),
                new Interest("Skateboarding"),
                new Interest("Rollerblading")
        ))));

        categories.add(new InterestType("Combat Sports", new ArrayList<>(Arrays.asList(
                new Interest("Boxing"),
                new Interest("Karate"),
                new Interest("Judo"),
                new Interest("Taekwondo")
        ))));

        categories.add(new InterestType("Resistance Training", new ArrayList<>(Arrays.asList(
                new Interest("Weightlifting"),
                new Interest("Bodyweight Training")
        ))));

        categories.add(new InterestType("Winter Sports", new ArrayList<>(Arrays.asList(
                new Interest("Skiing"),
                new Interest("Snowboarding")
        ))));

        categories.add(new InterestType("Water Sports", new ArrayList<>(Arrays.asList(
                new Interest("Swimming"),
                new Interest("Surfing"),
                new Interest("Kayaking")
        ))));

        categories.add(new InterestType("Other Sports", new ArrayList<>(Arrays.asList(
                new Interest("Climbing"),
                new Interest("Archery"),
                new Interest("Horse Riding")
        ))));

        return categories;
    }
}

