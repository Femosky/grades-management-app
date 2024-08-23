package com.example.appforapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GradeEntryFragment extends Fragment {

    // Database helper object to interact with the database
    private DatabaseHelper db;

    // UI components for user input
    private EditText nameEditText, gradeEditText, durationEditText, feesEditText;
    private ListView programCodeListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade_entry, container, false);

        // Initialize the database helper
        db = new DatabaseHelper(getActivity());

        // Initialize the UI components
        nameEditText = view.findViewById(R.id.editTextName);
        gradeEditText = view.findViewById(R.id.editTextGrade);
        durationEditText = view.findViewById(R.id.editTextDuration);
        feesEditText = view.findViewById(R.id.editTextFees);
        programCodeListView = view.findViewById(R.id.listViewProgramCode);
        Button submitButton = view.findViewById(R.id.buttonSubmit);

        // Set up the ListView with program codes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_single_choice, getResources().getStringArray(R.array.program_codes));
        programCodeListView.setAdapter(adapter);
        programCodeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set up the submit button click listener
        submitButton.setOnClickListener(v -> {
            // Hide the keyboard when the button is clicked
            KeyboardUtil.hideKeyboard(v);

            // Get user input from EditText and ListView
            String name = nameEditText.getText().toString();
            String grade = gradeEditText.getText().toString();
            String duration = durationEditText.getText().toString();
            String fees = feesEditText.getText().toString();
            int position = programCodeListView.getCheckedItemPosition();

            // Check if any field is empty or no program code is selected
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(grade) || TextUtils.isEmpty(duration) || TextUtils.isEmpty(fees) || position == ListView.INVALID_POSITION) {
                Toast.makeText(getActivity(), "Please fill all fields and select a program code", Toast.LENGTH_SHORT).show();
            } else {
                // Get the selected program code
                String programCode = programCodeListView.getItemAtPosition(position).toString();

                // Insert the data into the database
                boolean isInserted = db.insertData(name, programCode, grade, duration, fees);

                if (isInserted) {
                    // Show a success message
                    Toast.makeText(getActivity(), "Grade Successfully Saved", Toast.LENGTH_SHORT).show();
                } else {
                    // Show a failure message
                    Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Return the view for this fragment
        return view;
    }
}
