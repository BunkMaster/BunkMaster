/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ayush_harshit.bunkmaster.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ayush_harshit.bunkmaster.Data.SubjectContract.SubjectEntry;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class SubjectDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SubjectDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "course.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SubjectDbHelper}.
     *
     * @param context of the app
     */
    public SubjectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + SubjectContract.SubjectEntry.TABLE_NAME + " ("
                + SubjectContract.SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SubjectContract.SubjectEntry.COLUMN_COURSE_NAME + " TEXT NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_COURSE_CODE + " TEXT NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION + " INTEGER NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK + " INTEGER NOT NULL, "
                + SubjectContract.SubjectEntry.COLUMN_ATTENDANCE + " FLOAT NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}