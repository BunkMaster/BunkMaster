package com.example.ayush_harshit.bunkmaster.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by dell pc on 02-09-2017.
 */
public class SubjectProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = SubjectProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */
    private static SubjectDbHelper mDbHelper;
    /** URI matcher code for the content URI for the pets table */
    public static final int SUBJECTS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    public static final int SUBJECT_ID = 101;

    /** URI matcher object to match a context URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #SUBJECTS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(SubjectContract.CONTENT_AUTHORITY, SubjectContract.PATH_SUBJECTS, SUBJECTS);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PETS_ID}. This URI is used to provide access to ONE single row
        // of the pets table.

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SubjectContract.CONTENT_AUTHORITY, SubjectContract.PATH_SUBJECTS + "/#", SUBJECT_ID);
    }
    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a SubjectDbHelper object to gain access to the pets database.
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        mDbHelper = new SubjectDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case SUBJECTS:
                // For the SUBJECTS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor = database.query(SubjectContract.SubjectEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            case SUBJECT_ID:
                // For the SUBJECT_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = SubjectContract.SubjectEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(SubjectContract.SubjectEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                return insertSubject(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertSubject(Uri uri, ContentValues values) {

        // TODO: Insert a new pet into the pets database table with the given ContentValues
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String name = values.getAsString(SubjectContract.SubjectEntry.COLUMN_COURSE_NAME);
        if(name == null)
            throw new IllegalArgumentException("Subject requires a valid name");

        String code = values.getAsString(SubjectContract.SubjectEntry.COLUMN_COURSE_CODE);
        if(code == null)
            throw new IllegalArgumentException("Subject has to have a code");

        String duration = values.getAsString(SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION);
        if(duration == null)
            throw new IllegalArgumentException("Subject has to have a valid duration");

        String lectures = values.getAsString(SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK);

        String attendance = values.getAsString(SubjectContract.SubjectEntry.COLUMN_ATTENDANCE);

        /**Cannot resolve isGenderValid() function
         */
        /*Integer gender =values.getAsInteger(SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION);
        if(gender == null || !SubjectContract.SubjectEntry.isGenderValid(gender))
            throw new IllegalArgumentException("Pet cannot be without a gender");
        */

        long id = db.insert(SubjectContract.SubjectEntry.TABLE_NAME,null,values);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                return updateSubject(uri, contentValues, selection, selectionArgs);
            case SUBJECT_ID:
                // For the SUBJECT_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = SubjectContract.SubjectEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateSubject(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateSubject(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // TODO: Update the selected pets in the pets database table with the given ContentValues
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        /**Cannot resolve isGenderValid() function
         */
        /*Integer gender =values.getAsInteger(SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION);
        if(gender == null || !SubjectContract.SubjectEntry.isGenderValid(gender))
            throw new IllegalArgumentException("Pet cannot be without a gender");
        */

        int numberOfRowsUpdated = db.update(SubjectContract.SubjectEntry.TABLE_NAME,values,selection,selectionArgs);

        if(numberOfRowsUpdated!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        // TODO: Return the number of rows that were affected
        return numberOfRowsUpdated;
    }


    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int nRowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                // Delete all rows that match the selection and selection args
                return database.delete(SubjectContract.SubjectEntry.TABLE_NAME, selection, selectionArgs);
            case SUBJECT_ID:
                // Delete a single row given by the ID in the URI
                selection = SubjectContract.SubjectEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                nRowsDeleted = database.delete(SubjectContract.SubjectEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if(nRowsDeleted!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return nRowsDeleted;
    }


    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                return SubjectContract.CONTENT_LIST_TYPE;
            case SUBJECT_ID:
                return SubjectContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
