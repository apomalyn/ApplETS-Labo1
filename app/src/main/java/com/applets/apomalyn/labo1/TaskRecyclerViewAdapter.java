package com.applets.apomalyn.labo1;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.applets.apomalyn.labo1.TaskFragment.OnListFragmentInteractionListener;
import com.applets.apomalyn.labo1.task.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy - h:m a", Locale.ENGLISH);

        holder.mItem = mValues.get(position);
        holder.checkBox.setChecked(holder.mItem.isCompleted());
        holder.nameText.setText(holder.mItem.getName());

        if(holder.mItem.isCompleted()){
            holder.dateCompletedText.setText(format.format(holder.mItem.getCompletedDate()));
            holder.nameText.setPaintFlags(holder.nameText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(null != mListener){
                    mListener.onListCheckBoxChange(holder.mItem, b);
                    holder.update();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox checkBox;
        public final TextView nameText;
        public final TextView dateCompletedText;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            checkBox = view.findViewById(R.id.checkBox);
            nameText = view.findViewById(R.id.name);
            dateCompletedText = view.findViewById(R.id.dateCompleted);
        }

        public void update(){
            DateFormat format = new SimpleDateFormat("MMM dd, yyyy - h:m a", Locale.ENGLISH);
            if(mItem.isCompleted()){
                dateCompletedText.setText(format.format(mItem.getCompletedDate()));
                nameText.setPaintFlags(nameText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                dateCompletedText.setText("");
                nameText.setPaintFlags(nameText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameText.getText() + "'";
        }
    }
}
