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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class SubjectContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SubjectContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.ayush_harshit.bunkmaster";

    public static final String PATH_SUBJECTS = "bunkmaster";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    /**
     * The MIME type of the {@link #} for a list of pets.
     */
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECTS;

    /**
     * The MIME type of the {@link #} for a single pet.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECTS;


    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class SubjectEntry implements BaseColumns {

        /** Name of database table for pets */
        public final static String TABLE_NAME = "subjects";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_COURSE_NAME ="name";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_COURSE_CODE = "code";

        public final static String COLUMN_COURSE_DURATION = "duration";

        /**
         * Weight of the pet.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_LECTURES_PER_WEEK = "lectures";

        public final static String COLUMN_ATTENDANCE = "attendance";


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SUBJECTS);

    }

}

