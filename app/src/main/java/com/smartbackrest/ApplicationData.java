package com.smartbackrest;

public class ApplicationData {
    private static final ApplicationData ourInstance = new ApplicationData();

    public static ApplicationData getInstance() {
        return ourInstance;
    }

    private ApplicationData() {
    }

    private User user;
    private MealType mealType;
    private long mealTime;
    private MealContent mealContent;

    public User getUser() {
        return user == null ? user = new User() : user;
    }

    public ApplicationData setUser(User user) {
        this.user = user;
        return this;
    }

    public MealType getMealType() {
        return mealType;
    }

    public ApplicationData setMealType(MealType mealType) {
        this.mealType = mealType;
        return this;
    }


    public long getMealTime() {
        return mealTime;
    }

    public ApplicationData setMealTime(long mealTime) {
        this.mealTime = mealTime;
        return this;
    }

    public MealContent getMealContent() {
        return mealContent == null ? mealContent = new MealContent() : mealContent;
    }

    public ApplicationData setMealContent(MealContent mealContent) {
        this.mealContent = mealContent;
        return this;
    }

    public void setMealToNone() {
        if (mealContent != null) {
            mealContent.setFruit(MealSize.NONE);
            mealContent.setFried(null);
            mealContent.setRice(MealSize.NONE);
            mealContent.setSoup(MealSize.NONE);
            mealContent.setProtein(MealSize.NONE);
        }
    }

    public void clear() {
        user = null;
        mealType = null;
        mealTime = -1l;
        mealContent = null;
    }
}