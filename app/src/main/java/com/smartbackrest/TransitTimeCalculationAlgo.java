package com.smartbackrest;

import android.util.Log;

import java.util.Calendar;

class TransitTimeCalculationAlgo {

    private static final String TAG = "TransitTimeCalculationA";

    int calculateTime(User user, MealType mealType, MealContent mealContent) {
        Log.i(TAG, String.format("calculateTime: user is %s", user.toString()));
        int minutes = 0;

        switch (mealType) {
            case LIQUID: {
                minutes += 80;
                break;
            }
            case SEMI_SOLID: {
                minutes += 120;
                break;
            }
            default: {
                minutes += 180;
                break;
            }
        }

        /*final Calendar instance = Calendar.getInstance();
        int currentYear = instance.get(Calendar.YEAR);
        instance.setTimeInMillis(user.getDob());
        int birthYear = instance.get(Calendar.YEAR);
        int age = currentYear - birthYear;*/

        int age = user.getDob();

        if (age > 10 && age < 30) {
            minutes += 5;
        } else if (age < 50) {
            minutes += 10;
        } else if (age < 70) {
            minutes += 15;
        } else if (age < 90) {
            minutes += 20;
        } else {
            minutes += 30;
        }

        if (user.getGender() != 1) minutes += 5;

        if (user.isHasDiabetes()) minutes += 20;
        if (user.isHasAsthama()) minutes += 20;
        if (user.isHasParkinsons()) minutes += 30;
        if (user.isHasScleroderma()) minutes += 30;
        if (user.isHasMultipleSclerosis()) minutes += 10;

        if (user.isOnDigestiveMedication()) minutes += 20;

        if (user.isHasAnxietyIssues()) minutes += 5;
        if (user.isHasStomachSurgeries()) minutes += 5;
        if (user.isHasPain()) minutes += 5;

        if (mealContent.isFried() != null) {
            if (mealContent.isFried()) minutes += 15;
        }

        if (mealContent.getFruit() != MealSize.NONE) {
            if (mealContent.getFruit() == MealSize.SMALL) minutes += 10;
            if (mealContent.getFruit() == MealSize.MEDIUM) minutes += 15;
            if (mealContent.getFruit() == MealSize.LARGE || mealContent.getFruit() == MealSize.NOT_SURE)
                minutes += 20;
        }

        if (mealContent.getProtein() != null) {
            if (mealContent.getProtein() == MealSize.SMALL) minutes += 15;
            if (mealContent.getProtein() == MealSize.MEDIUM) minutes += 20;
            if (mealContent.getProtein() == MealSize.LARGE || mealContent.getProtein() == MealSize.NOT_SURE)
                minutes += 25;
        }

        if (mealContent.getRice() != null) {
            if (mealContent.getRice() == MealSize.SMALL) minutes += 5;
            if (mealContent.getRice() == MealSize.MEDIUM) minutes += 10;
            if (mealContent.getRice() == MealSize.LARGE || mealContent.getRice() == MealSize.NOT_SURE)
                minutes += 15;
        }

        if (mealContent.getSoup() == MealSize.SMALL) minutes += 5;
        if (mealContent.getSoup() == MealSize.MEDIUM) minutes += 10;
        if (mealContent.getSoup() == MealSize.LARGE || mealContent.getSoup() == MealSize.NOT_SURE)
            minutes += 15;

        Log.i(TAG, String.format("calculateTime: Total minutes added is %s", minutes));
        return minutes;
    }
}
