package com.example.ayush_harshit.bunkmaster.Adapters;

/**
 * Created by ayush on 5/2/18.
 */
import com.example.ayush_harshit.bunkmaster.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder{

        public TextView textView;

        public MyView(View myView){
            super(myView);

            textView = (TextView)myView.findViewById(R.id.textView1);
        }
    }

    public RecyclerViewAdapter (List<String> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_recycler_view,parent,false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

