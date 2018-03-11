package com.example.ayush_harshit.bunkmaster.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ayush_harshit.bunkmaster.Adapters.CLickListener;
import com.example.ayush_harshit.bunkmaster.Adapters.Subject;
//import com.example.ayush_harshit.bunkmaster.Adapters.SubjectListAdapter;
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

    private CLickListener cLickListener = null;

    private Cursor mCursor;

    private boolean mDataValid;

    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    public SubjectCursorAdapter(Context context, Cursor c) {

        mContext = context;

        mCursor = c;
        mDataValid = c != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }

        mCursorAdapter = new CursorAdapter(mContext, c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // Inflate the view here
                return LayoutInflater.from(context).inflate
                        (R.layout.subject_list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Binding operations
                final TextView courseCodeView = (TextView) view.findViewById(R.id.tvCourseCode);
                final TextView courseNameView = (TextView) view.findViewById(R.id.tvCourseName);
                final TextView attendanceRequiredView = (TextView) view.findViewById
                        (R.id.tvCourseAttendance);

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

    public void setcLickListener(CLickListener cLickListener) {
        this.cLickListener = cLickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView tvCourseCode;
        public TextView tvCourseName;
        public TextView tvCourseAttendance;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvCourseCode = (TextView)itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = (TextView)itemView.findViewById(R.id.tvCourseName);
            tvCourseAttendance = (TextView)itemView.findViewById(R.id.tvCourseAttendance);
        }

        @Override
        public void onClick(View v) {
            if (cLickListener!=null)
                cLickListener.itemClicked(v,getAdapterPosition());
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

        mCursorAdapter.getCursor().moveToPosition(position);

        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());

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

        /*Subject currentSubject = mCursorAdapter.get;
        holder.tvCourseCode.setText(currentSubject.getTvCourseCode());
        holder.tvCourseName.setText(currentSubject.getTvCourseName());
        holder.tvCourseAttendance.setText(currentSubject.getTvCourseAttendance() + "%");

        */
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     */
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
    }
    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    /*public void setData(List<Subject> mSubjects){
        subjects = mSubjects;
        notifyDataSetChanged();
    }
    */

}