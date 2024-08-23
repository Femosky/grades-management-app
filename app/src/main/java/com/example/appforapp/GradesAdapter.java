package com.example.appforapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {

    // List to hold grade data
    private ArrayList<Grade> gradeList;

    // Constructor to initialize the adapter with the grade list
    public GradesAdapter(ArrayList<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    // Inflates the item layout and creates the ViewHolder
    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item, parent, false);
        return new GradeViewHolder(view);
    }

    // Binds data to the views in each ViewHolder
    @SuppressLint("SetTextI18n") // Just to suppress the string resource variable requirments
    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = gradeList.get(position);

        // Set the data for each text view
        holder.idTextView.setText("ID: " + grade.getId());
        holder.nameTextView.setText("Name: " + grade.getName());
        holder.programCodeTextView.setText("Program Code: " + grade.getProgramCode());
        holder.gradeTextView.setText("Grade: " + grade.getGrade());
        holder.durationTextView.setText("Duration: " + grade.getDuration());
        holder.feesTextView.setText("Fees: " + grade.getFees());
    }

    // Returns the total number of items in the data set held by the adapter
    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, nameTextView, programCodeTextView, gradeTextView, durationTextView, feesTextView;

        // Constructor to initialize the views in the item layout
        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.textViewId);
            nameTextView = itemView.findViewById(R.id.textViewName);
            programCodeTextView = itemView.findViewById(R.id.textViewProgramCode);
            gradeTextView = itemView.findViewById(R.id.textViewGrade);
            durationTextView = itemView.findViewById(R.id.textViewDuration);
            feesTextView = itemView.findViewById(R.id.textViewFees);
        }
    }
}
