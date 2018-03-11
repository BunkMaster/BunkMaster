package com.example.ayush_harshit.bunkmaster.Data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ayush_harshit.bunkmaster.Adapters.Subject;
import com.example.ayush_harshit.bunkmaster.Adapters.SubjectListAdapter;
import com.example.ayush_harshit.bunkmaster.R;

import java.util.List;

/**
 * {@link SubjectCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class SubjectCursorAdapter extends RecyclerView.Adapter<SubjectCursorAdapter.ViewHolder> {

    /**
     * Constructs a new {@link SubjectCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    CursorAdapter mCursorAdapter;

    Context mContext;

    public SubjectCursorAdapter(Context context, Cursor c) {

        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // Inflate the view here
                return LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Binding operations
                final TextView courseCodeView = (TextView) view.findViewById(R.id.etCourseCode);
                final TextView courseNameView = (TextView) view.findViewById(R.id.etCourseName);
                final TextView attendanceRequiredView = (TextView) view.findViewById(R.id.etAttendanceRequired);

                int nameColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_COURSE_NAME);
                int codeColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_COURSE_CODE);
                int attendanceColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_ATTENDANCE);

                String name = cursor.getString(nameColumnIndex);
                String code = cursor.getString(codeColumnIndex);
                String attendance = cursor.getString(attendanceColumnIndex);

                courseCodeView.setText(code);
                courseNameView.setText(name);
                attendanceRequiredView.setText(attendance);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCourseCode;
        public TextView tvCourseName;
        public TextView tvCourseAttendance;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCourseCode = (TextView)itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = (TextView)itemView.findViewById(R.id.tvCourseName);
            tvCourseAttendance = (TextView)itemView.findViewById(R.id.tvCourseAttendance);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public SubjectCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item,parent,false);
        View itemView = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject currentSubject = mCursorAdapter.get;
        holder.tvCourseCode.setText(currentSubject.getTvCourseCode());
        holder.tvCourseName.setText(currentSubject.getTvCourseName());
        holder.tvCourseAttendance.setText(currentSubject.getTvCourseAttendance() + "%");
        if(position%2 == 1){
            holder.itemView.setBackgroundColor(Color.GRAY);
            holder.tvCourseName.setTextColor(Color.WHITE);
            holder.tvCourseAttendance.setTextColor(Color.WHITE);
        }

        else{
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvCourseName.setTextColor(Color.GRAY);
            holder.tvCourseAttendance.setTextColor(Color.GRAY);
        }
    };

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        // TODO: Fill out this method and return the list item view (instead of null)
//        return LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
//    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO: Fill out this method
//        final TextView courseCodeView = (TextView)view.findViewById(R.id.etCourseCode);
//        final TextView courseNameView = (TextView)view.findViewById(R.id.etCourseName);
//        final TextView attendanceRequiredView = (TextView)view.findViewById(R.id.etAttendanceRequired);
//
//        int nameColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_COURSE_NAME);
//        int codeColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_COURSE_CODE);
//        int attendanceColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.COLUMN_ATTENDANCE);
//
//        String name = cursor.getString(nameColumnIndex);
//        String code = cursor.getString(codeColumnIndex);
//        String attendance = cursor.getString(attendanceColumnIndex);
//
//        courseCodeView.setText(code);
//        courseNameView.setText(name);
//        attendanceRequiredView.setText(attendance);
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    public void setData(List<Subject> mSubjects){
        subjects = mSubjects;
        notifyDataSetChanged();
    }
}