package com.example.ayush_harshit.bunkmaster.Adapters;

/**
 * Created by ayush on 5/2/18.
 */
import com.example.ayush_harshit.bunkmaster.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    private List<Subject> subjects;
    private Context mContext;

    public SubjectListAdapter(Context context, ArrayList<Subject> subjects) {
        mContext = context;
        this.subjects = subjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.tvCourseCode) TextView tvCourseCode;
        //@BindView(R.id.tvCourseName) TextView tvCourseName;
        //@BindView(R.id.tvCourseAttendance) TextView tvCourseAttendance;

        public TextView tvCourseCode;
        public TextView tvCourseName;
        public TextView tvCourseAttendance;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCourseCode = (TextView)itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = (TextView)itemView.findViewById(R.id.tvCourseName);
            tvCourseAttendance = (TextView)itemView.findViewById(R.id.tvCourseAttendance);
            //ButterKnife.bind(this, itemView);
            //mContext = itemView.getContext();
        }

        /*public void bind(Subject subject) {
            tvCourseCode.setText(subject.getTvCourseCode());
            tvCourseName.setText(subject.getTvCourseName());
            tvCourseAttendance.setText(subject.getTvCourseAttendance() + "%");
        }*/
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item,parent,false);
        ViewHolder subjectViewHolder = new ViewHolder(itemView);
        return subjectViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject currentSubject = subjects.get(position);
        holder.tvCourseCode.setText(currentSubject.getTvCourseCode());
        holder.tvCourseName.setText(currentSubject.getTvCourseName());
        holder.tvCourseAttendance.setText(currentSubject.getTvCourseAttendance() + "%");
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}

