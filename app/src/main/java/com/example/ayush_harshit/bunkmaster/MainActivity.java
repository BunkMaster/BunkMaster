package com.example.ayush_harshit.bunkmaster;

/**
 * Created by ayush on 5/2/18.
 */
import com.example.ayush_harshit.bunkmaster.Adapters.Subject;
import com.example.ayush_harshit.bunkmaster.Adapters.SubjectListAdapter;
import com.example.ayush_harshit.bunkmaster.Data.SubjectContract;
import com.example.ayush_harshit.bunkmaster.Data.SubjectCursorAdapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Map;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * The launchpad activity for this sample project. This activity launches other activities that
 * demonstrate implementations of common animations.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This class describes an individual sample (the sample title, and the activity class that
     * demonstrates this sample).
     */
    FloatingActionButton floatingActionButton;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    private SubjectCursorAdapter mCursorAdapter;
    /*SubjectListAdapter recyclerViewHorizontalAdapter;
    LinearLayoutManager horizontalLayoutManager;
    View childView;
    int recyclerViewItemPosition;*/

    //@BindViews(R.id.recyclerView) RecyclerView mRecyclerView;
    private SubjectListAdapter mAdapter;

    private static int PET_LOADER = 0;

    public ArrayList<Subject> subjects;

    public static final String courseCode = "CourseCode";
    public static final String courseName = "CourseName";
    public static final String courseDuration = "CourseDuration";
    public static final String lecturesPreWeek = "LecturesPerWeek";
    public static final String attendanceRequired = "AttendanceRequired";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife.bind(this);
        //Intent intent = getIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerViewLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubjectListAdapter(getBaseContext(), subjects);
        mRecyclerView.setAdapter(mAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.addSubjects);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        AddASubject.class));
            }
        });

        //recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        //mRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        //Add items to the arraylist
        displayDatabaseInfo();

    }

    // function to add items in RecyclerView.
//    public void AddSubjectsToRecyclerViewArrayList() {
//
//        subjects = new ArrayList<Subject>();
//
//        subjects.add(new Subject(sharedPreferences.getString(courseCode, null),"Introduction to" +
//                " Crytography and Security",
//                "90"));
//        mAdapter.setData(subjects);
//
//
//    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SubjectContract.SubjectEntry._ID,
                SubjectContract.SubjectEntry.COLUMN_COURSE_NAME,
                SubjectContract.SubjectEntry.COLUMN_COURSE_CODE,
                SubjectContract.SubjectEntry.COLUMN_COURSE_DURATION,
                SubjectContract.SubjectEntry.COLUMN_LECTURES_PER_WEEK
                SubjectContract.SubjectEntry.COLUMN_ATTENDANCE};

        // Perform a query on the pets table
        Cursor cursor = getContentResolver().query(
                SubjectContract.SubjectEntry.CONTENT_URI,   // The table to query
                projection,            // The columns to return
                null,                  // selection
                null,                  // selectionArgs[]
                null);                   // The sort order

        SubjectCursorAdapter adapter = new SubjectCursorAdapter(this,cursor);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(adapter);

        View emptyView = (View)findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(emptyView);

        mCursorAdapter = new SubjectCursorAdapter(this,cursor);
        mRecyclerView.setAdapter(mCursorAdapter);

        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(getApplicationContext(),AddASubject.class);

                Uri currentPetUri = ContentUris.withAppendedId(SubjectContract.SubjectEntry.CONTENT_URI,id);

                editIntent.setData(currentPetUri);

                startActivity(editIntent);
            }
        });

        getLoaderManager().initLoader(PET_LOADER,null,null);
    }
}

