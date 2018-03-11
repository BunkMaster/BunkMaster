package com.example.ayush_harshit.bunkmaster.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell pc on 28-08-2017.
 */
public class FeedHelper extends SQLiteOpenHelper {

    private static String database_name = "course.db";
    private static final int database_version = 1;

    public FeedHelper(Context context){
        super(context,database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE "+ SubjectContract.SubjectEntry.TABLE_NAME+ "("+
                SubjectContract.SubjectEntry._ID+ "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SubjectContract.SubjectEntry.COLUMN_COURSE_NAME +" TEXT NOT NULL,"
                + SubjectContract.SubjectEntry.COLUMN_COURSE_CODE +" TEXT NOT NULL,"
                + SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION + " INTEGER NOT NULL,"
                + SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK +" INTEGER NOT NULL DEFAULT 0"
                + SubjectContract.SubjectEntry.COLUMN_ATTENDANCE+"FLOAT NOT NULL DEFAULT 0";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
