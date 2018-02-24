package com.example.ayush_harshit.bunkmaster;

/**
 * Created by ayush on 5/2/18.
 */
import com.example.ayush_harshit.bunkmaster.Adapters.RecyclerViewAdapter;
import com.example.ayush_harshit.bunkmaster.R;

import android.app.Activity;
import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

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
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerViewAdapter recyclerViewHorizontalAdapter;
    LinearLayoutManager horizontalLayoutManager;
    ArrayList<String> options;
    View childView;
    int recyclerViewItemPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.addSubjects);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        AddSubjects.class));
            }
        });

        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        //Add items to the arraylist
        AddItemsToRecyclerViewArrayList();

        recyclerViewHorizontalAdapter = new RecyclerViewAdapter(options);

        //horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(horizontalLayoutManager);

        recyclerView.setAdapter(recyclerViewHorizontalAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return super.onSingleTapUp(e);
                        }
                    });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                childView = rv.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && gestureDetector.onTouchEvent(e)) {
                    recyclerViewItemPosition = rv.getChildAdapterPosition(childView);
                    Toast.makeText(MainActivity.this, options.get(recyclerViewItemPosition),
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                childView = rv.findChildViewUnder(e.getX(), e.getY());
                recyclerViewItemPosition = rv.getChildAdapterPosition(childView);
                /*if (recyclerViewItemPosition == 0) {
                    startActivity(new Intent(MainActivity.this,
                            CrossfadeActivity.class));
                }
                if (recyclerViewItemPosition == 1) {
                    startActivity(new Intent(MainActivity.this,
                            CardFlipActivity.class));
                }
                if (recyclerViewItemPosition == 2) {
                    startActivity(new Intent(MainActivity.this,
                            ScreenSlideActivity.class));
                }
                if (recyclerViewItemPosition == 3) {
                    startActivity(new Intent(MainActivity.this,
                            ZoomActivity.class));
                }
                if (recyclerViewItemPosition == 4) {
                    startActivity(new Intent(MainActivity.this,
                            LayoutChangesActivity.class));
                }
                */
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    // function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList() {

        options = new ArrayList<>();
        options.add("Simple Crossfade");
        options.add("Card Flip");
        options.add("Screen Slide");
        options.add("Zoom");
        options.add("Layout change");
        options.add("Mood change");
        options.add("Dress change");
        options.add("Topic change");

    }
}