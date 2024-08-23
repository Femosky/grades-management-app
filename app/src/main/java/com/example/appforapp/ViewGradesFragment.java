package com.example.appforapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewGradesFragment extends Fragment {

    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_grades, container, false);

        // Initialize the database helper
        DatabaseHelper db = new DatabaseHelper(getActivity());

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGrades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the list and adapter for grades
        ArrayList<Grade> gradeList = new ArrayList<>();
        GradesAdapter adapter = new GradesAdapter(gradeList);
        recyclerView.setAdapter(adapter);

        // Fetch all data from the database
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            // Show a message if no data is found
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            // Populate the grade list with data from the database
            while (res.moveToNext()) {
                gradeList.add(new Grade(
                        res.getString(0),
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5)
                ));
            }
            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }

        // Return the view for this fragment
        return view;
    }
}
