package com.example.ayush_harshit.bunkmaster;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AddASubject extends AppCompatActivity {

    public static final String courseCode = "CourseCode";
    public static final String courseName = "CourseName";
    public static final String courseDuration = "CourseDuration";
    public static final String lecturesPreWeek = "LecturesPerWeek";
    public static final String attendanceRequired = "AttendanceRequired";

    final EditText courseCodeText = (EditText) findViewById(R.id.etCourseCode);
    final EditText courseNameText = (EditText)findViewById(R.id.etCourseName);
    final EditText courseDurationText = (EditText)findViewById(R.id.etCourseDuration);
    final EditText lecturesPreWeekText = (EditText)findViewById(R.id.etPerWeek);
    final EditText attendanceRequiredText = (EditText)findViewById(R.id.etAttendanceRequired);

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

        courseCodeText.setOnTouchListener(mTouchListener);
        courseNameText.setOnTouchListener(mTouchListener);
        courseDurationText.setOnTouchListener(mTouchListener);
        lecturesPreWeekText.setOnTouchListener(mTouchListener);
        attendanceRequiredText.setOnTouchListener(mTouchListener);

        Button done = (Button) findViewById(R.id.btDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void saveSubject(){
        String name = courseNameText.getText().toString().trim();
        String code = courseCodeText.getText().toString().trim();
        String duration = courseDurationText.getText().toString().trim();
        String lectures = lecturesPreWeekText.getText().toString().trim();
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


}
