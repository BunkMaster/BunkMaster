package com.example.ayush_harshit.bunkmaster;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush_harshit.bunkmaster.Adapters.Subject;
import com.example.ayush_harshit.bunkmaster.Data.SubjectContract;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

public class AddASubject extends AppCompatActivity implements
                            LoaderManager.LoaderCallbacks<Cursor>{

    public static final String courseCode = "CourseCode";
    public static final String courseName = "CourseName";
    public static final String courseDuration = "CourseDuration";
    public static final String lecturesPreWeek = "LecturesPerWeek";
    public static final String attendanceRequired = "AttendanceRequired";

    EditText courseCodeText,courseNameText,courseDurationText,
            lecturesPerWeekText,attendanceRequiredText;


    private Uri currentSubjectUri;

    private boolean subjectChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            subjectChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent();
        currentSubjectUri = intent.getData();

        courseCodeText = (EditText) findViewById(R.id.etCourseCode);
        courseNameText = (EditText)findViewById(R.id.etCourseName);
        courseDurationText = (EditText)findViewById(R.id.etCourseDuration);
        lecturesPerWeekText = (EditText)findViewById(R.id.etPerWeek);
        attendanceRequiredText = (EditText)findViewById(R.id.etAttendanceRequired);

        courseCodeText.setOnTouchListener(mTouchListener);
        courseNameText.setOnTouchListener(mTouchListener);
        courseDurationText.setOnTouchListener(mTouchListener);
        lecturesPerWeekText.setOnTouchListener(mTouchListener);
        attendanceRequiredText.setOnTouchListener(mTouchListener);

        Button done = (Button) findViewById(R.id.btDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSubject();
                finish();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //private void insertSubject() {
        // Gets the database in write mode
        ////SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.

        /*ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);



        ContentValues contentValues = new ContentValues();

        /*contentValues.put(SubjectContract.SubjectEntry.COLUMN_PET_NAME,"Toto");
        contentValues.put(PetEntry.COLUMN_PET_BREED,"Terrier");
        contentValues.put(PetEntry.COLUMN_PET_GENDER,PetEntry.GENDER_MALE);
        contentValues.put(PetEntry.COLUMN_PET_WEIGHT,7);
        */

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.

        //Uri newUri = getContentResolver().insert
      //          (SubjectContract.SubjectEntry.CONTENT_URI,contentValues);
    //}


    private void saveSubject(){
        String name = courseNameText.getText().toString().trim();
        String code = courseCodeText.getText().toString().trim();
        String duration = courseDurationText.getText().toString().trim();
        String lectures = lecturesPerWeekText.getText().toString().trim();
        String attendance = attendanceRequiredText.getText().toString().trim();

        int numberOfLectures = 0;
        if(!TextUtils.isEmpty(lectures))
            numberOfLectures = Integer.parseInt(lectures);

        int coursDuration = 0;
        if(!TextUtils.isEmpty(duration))
            coursDuration = Integer.parseInt(duration);

        float attendanceRequired = 0.0f;
        if(!TextUtils.isEmpty(attendance))
            attendanceRequired = Float.parseFloat(attendance);

        if(currentSubjectUri == null && TextUtils.isEmpty(name) && TextUtils.isEmpty(code) && TextUtils.isEmpty(duration)
                && TextUtils.isEmpty(lectures) && TextUtils.isEmpty(attendance)){
            return;
        }

        else if(currentSubjectUri==null){
            ContentValues mInsertValues = new ContentValues();
            mInsertValues.put(SubjectContract.SubjectEntry.COLUMN_COURSE_NAME,name);
            mInsertValues.put(SubjectContract.SubjectEntry.COLUMN_COURSE_CODE,code);
            mInsertValues.put(SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION,coursDuration);
            mInsertValues.put(SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK,numberOfLectures);
            mInsertValues.put(SubjectContract.SubjectEntry.COLUMN_ATTENDANCE,attendanceRequired);

            Uri newUri = getContentResolver().insert(SubjectContract.SubjectEntry.CONTENT_URI, mInsertValues);

            if(newUri == null)
                Toast.makeText(this,R.string.error_saving,Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,R.string.subject_saved,Toast.LENGTH_SHORT).show();

        }

        else {
            ContentValues mUpdateValues = new ContentValues();
            //mUpdateValues.putNull(PetEntry);
            int nRowsAffected = getContentResolver().update(currentSubjectUri,mUpdateValues,null,null);
            if(nRowsAffected == 0)
                Toast.makeText(this,"Updating of subject failed",Toast.LENGTH_SHORT);
            else
                Toast.makeText(this,"Updating of subject successful",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String projection[] = {
                SubjectContract.SubjectEntry.COLUMN_COURSE_NAME,
                SubjectContract.SubjectEntry.COLUMN_COURSE_CODE,
                SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION,
                SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK,
                SubjectContract.SubjectEntry.COLUMN_ATTENDANCE};

        return new CursorLoader(this,currentSubjectUri,projection,
                null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if(cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex
                    (SubjectContract.SubjectEntry.COLUMN_COURSE_NAME);
            int codeColumnIndex = cursor.getColumnIndex
                    (SubjectContract.SubjectEntry.COLUMN_COURSE_CODE);
            int durationColumnIndex = cursor.getColumnIndex
                    (SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION);
            int lecturesColumnIndex = cursor.getColumnIndex
                    (SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK);
            int attendanceColumnIndex = cursor.getColumnIndex
                    (SubjectContract.SubjectEntry.COLUMN_ATTENDANCE);


            String name = cursor.getString(nameColumnIndex);
            String code = cursor.getString(codeColumnIndex);
            int duration = cursor.getInt(durationColumnIndex);
            int lectures = cursor.getInt(lecturesColumnIndex);
            int attendance = cursor.getInt(attendanceColumnIndex);

            courseNameText.setText(name);
            courseCodeText.setText(code);
            courseDurationText.setText(Integer.toString(duration));
            lecturesPerWeekText.setText(Integer.toString(lectures));
            attendanceRequiredText.setText(Integer.toString(attendance));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
