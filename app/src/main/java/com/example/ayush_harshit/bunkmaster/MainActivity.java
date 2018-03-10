package com.example.ayush_harshit.bunkmaster;

/**
 * Created by ayush on 5/2/18.
 */
import com.example.ayush_harshit.bunkmaster.Adapters.Subject;
import com.example.ayush_harshit.bunkmaster.Adapters.SubjectListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;

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
    /*SubjectListAdapter recyclerViewHorizontalAdapter;
    LinearLayoutManager horizontalLayoutManager;
    View childView;
    int recyclerViewItemPosition;*/

    //@BindViews(R.id.recyclerView) RecyclerView mRecyclerView;
    private SubjectListAdapter mAdapter;

    public ArrayList<Subject> subjects;

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
        AddSubjectsToRecyclerViewArrayList();

    }

    // function to add items in RecyclerView.
    public void AddSubjectsToRecyclerViewArrayList() {

        subjects = new ArrayList<Subject>();
        subjects.add(new Subject("CS-309","Introduction to" +
                " Crytography and Security",
                "90"));
        //mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemRangeChanged(0, subjects.size());

        subjects.add(new Subject("IT-302","Simple Crossfade",
                "95"));

        subjects.add(new Subject("IT-302","Information Security",
                "80"));

        subjects.add(new Subject("CS-303","Software Engineering",
                "75"));

        mAdapter.setData(subjects);


    }
}

