package com.smartbackrest;

public class MealContent {

    private MealSize soup = MealSize.NONE;
    private MealSize rice = MealSize.NONE;
    private MealSize fruit = MealSize.NONE;
    private MealSize protein = MealSize.NONE;
    private Boolean isFried = null;


    public MealSize getSoup() {
        return soup;
    }

    public MealContent setSoup(MealSize soup) {
        this.soup = soup;
        return this;
    }

    public MealSize getRice() {
        return rice;
    }

    public MealContent setRice(MealSize rice) {
        this.rice = rice;
        return this;
    }

    public MealSize getFruit() {
        return fruit;
    }

    public MealContent setFruit(MealSize fruit) {
        this.fruit = fruit;
        return this;
    }

    public MealSize getProtein() {
        return protein;
    }

    public MealContent setProtein(MealSize protein) {
        this.protein = protein;
        return this;
    }

    public Boolean isFried() {
        return isFried;
    }

    public MealContent setFried(Boolean fried) {
        isFried = fried;
        return this;
    }
}
