package com.smartbackrest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.smartbackrest.User;

import java.util.ArrayList;

public class DBManager {

    private static final String TAG = "DBManager";
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(User user) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.FIRST_NAME, user.getFirstName());
        contentValue.put(DatabaseHelper.LAST_NAME, user.getLastName());
        contentValue.put(DatabaseHelper.DOB, user.getDob());
        contentValue.put(DatabaseHelper.GENDER, user.getGender());
        contentValue.put(DatabaseHelper.HAS_ANXIETY_ISSUES, user.isHasAnxietyIssues() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_ASTHAMA, user.isHasAsthama() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_DIABETES, user.isHasDiabetes() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_MULTIPLE_SCLEROSIS, user.isHasMultipleSclerosis() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_PAIN, user.isHasPain() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_PARKINSONS, user.isHasParkinsons() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_SCLERODERMA, user.isHasScleroderma() ? 1 : 0);
        contentValue.put(DatabaseHelper.HAS_STOMACH_SURGERIES, user.isHasStomachSurgeries() ? 1 : 0);
        contentValue.put(DatabaseHelper.ON_DIGESTIVE_MEDICATION, user.isOnDigestiveMedication() ? 1 : 0);

        return database.insert(DatabaseHelper.USER_TABLE_NAME, null, contentValue);
    }

    public User fetchUser() {
        String[] columns = new String[]{DatabaseHelper._ID,
                DatabaseHelper.FIRST_NAME,
                DatabaseHelper.LAST_NAME,
                DatabaseHelper.DOB,
                DatabaseHelper.GENDER,
                DatabaseHelper.HAS_ANXIETY_ISSUES,
                DatabaseHelper.HAS_ASTHAMA,
                DatabaseHelper.HAS_DIABETES,
                DatabaseHelper.HAS_MULTIPLE_SCLEROSIS,
                DatabaseHelper.HAS_PAIN,
                DatabaseHelper.HAS_PARKINSONS,
                DatabaseHelper.HAS_SCLERODERMA,
                DatabaseHelper.HAS_STOMACH_SURGERIES,
                DatabaseHelper.ON_DIGESTIVE_MEDICATION};


        Cursor cursor = database.query(DatabaseHelper.USER_TABLE_NAME, columns, "", null, null, null, null);
        User user = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                user = new User()
                        .setFirstName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIRST_NAME)))
                        .setLastName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LAST_NAME)))
                        .setDob(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DOB)))
                        .setGender(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GENDER)))
                        .setHasAnxietyIssues(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_ANXIETY_ISSUES)) == 1)
                        .setHasAsthama(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_ASTHAMA)) == 1)
                        .setHasDiabetes(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_DIABETES)) == 1)
                        .setHasMultipleSclerosis(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_MULTIPLE_SCLEROSIS)) == 1)
                        .setHasPain(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_PAIN)) == 1)
                        .setHasParkinsons(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_PARKINSONS)) == 1)
                        .setHasScleroderma(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_SCLERODERMA)) == 1)
                        .setHasStomachSurgeries(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HAS_STOMACH_SURGERIES)) == 1)
                        .setOnDigestiveMedication(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ON_DIGESTIVE_MEDICATION)) == 1);
            }
            cursor.close();
        }
        return user;
    }

    public void clearUsers() {
        database.delete(DatabaseHelper.USER_TABLE_NAME, null, null);
    }
}