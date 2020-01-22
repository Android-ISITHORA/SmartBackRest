package com.smartbackrest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String USER_TABLE_NAME = "table_user";

    // Table columns
    public static final String _ID = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DOB = "dob";
    public static final String GENDER = "gender";
    public static final String HAS_DIABETES = "has_diabetes";
    public static final String HAS_ASTHAMA = "has_asthama";
    public static final String HAS_PARKINSONS = "has_parkinsons";
    public static final String HAS_SCLERODERMA = "has_scleroderma";
    public static final String HAS_MULTIPLE_SCLEROSIS = "has_multiple_sclerosis";
    public static final String ON_DIGESTIVE_MEDICATION = "on_digestive_medication";
    public static final String HAS_ANXIETY_ISSUES = "has_anxiety_issues";
    public static final String HAS_STOMACH_SURGERIES = "has_stomach_surgeries";
    public static final String HAS_PAIN = "has_pain";

    // Database Information
    static final String DB_NAME = "smartrest.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_USER = "create table " + USER_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LAST_NAME + " TEXT, "
            + DOB + " INTEGER, "
            + GENDER + " INTEGER, "
            + HAS_DIABETES + " INTEGER, "
            + HAS_ASTHAMA + " INTEGER, "
            + HAS_PARKINSONS + " INTEGER, "
            + HAS_SCLERODERMA + " INTEGER, "
            + HAS_MULTIPLE_SCLEROSIS + " INTEGER, "
            + ON_DIGESTIVE_MEDICATION + " INTEGER, "
            + HAS_ANXIETY_ISSUES + " INTEGER, "
            + HAS_STOMACH_SURGERIES + " INTEGER, "
            + HAS_PAIN + " INTEGER, "
            + FIRST_NAME + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}