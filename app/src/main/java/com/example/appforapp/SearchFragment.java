package com.example.appforapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    // Database helper object to interact with the database
    private DatabaseHelper db;

    // UI components for search options and results
    private RadioButton radioId, radioProgramCode;
    private EditText searchIdEditText;
    private ListView searchProgramCodeListView;
    private TextView idResultTextView;
    private GradesAdapter adapter;
    private ArrayList<Grade> searchResultsList;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize the database helper
        db = new DatabaseHelper(getActivity());

        // Initialize the UI components
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupSearch);
        radioId = view.findViewById(R.id.radioButtonId);
        radioProgramCode = view.findViewById(R.id.radioButtonProgramCode);
        searchIdEditText = view.findViewById(R.id.editTextSearchId);
        searchProgramCodeListView = view.findViewById(R.id.listViewSearchProgramCode);
        Button searchButton = view.findViewById(R.id.buttonSearch);
        idResultTextView = view.findViewById(R.id.textViewIdResult); // Initialize TextView for ID result
        RecyclerView searchResultsRecyclerView = view.findViewById(R.id.recyclerViewSearchResults);

        // Initialize the list and adapter for search results
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchResultsList = new ArrayList<>();
        adapter = new GradesAdapter(searchResultsList);
        searchResultsRecyclerView.setAdapter(adapter);

        // Set up the ListView with program codes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_single_choice, getResources().getStringArray(R.array.program_codes));
        searchProgramCodeListView.setAdapter(arrayAdapter);
        searchProgramCodeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Handle radio button selection changes
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonId) {
                // Show EditText for ID search
                searchIdEditText.setVisibility(View.VISIBLE);

                // Hide ListView for program code search
                searchProgramCodeListView.setVisibility(View.GONE);

                // Hide ID result TextView
                idResultTextView.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioButtonProgramCode) {
                // Hide EditText for ID search
                searchIdEditText.setVisibility(View.GONE);

                // Show ListView for program code search
                searchProgramCodeListView.setVisibility(View.VISIBLE);

                // Hide ID result TextView
                idResultTextView.setVisibility(View.GONE);
            }
        });

        // Handle search button clicks
        searchButton.setOnClickListener(v -> {
            KeyboardUtil.hideKeyboard(v); // Hide the keyboard when the button is clicked
            searchResultsList.clear(); // Clear previous search results
            adapter.notifyDataSetChanged(); // Notify adapter of data changes
            idResultTextView.setVisibility(View.GONE); // Hide ID result TextView before search

            if (radioId.isChecked()) {
                // Handle search by ID
                String id = searchIdEditText.getText().toString();
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getActivity(), "Please enter an ID", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor res = db.getDataById(id);
                    if (res.getCount() == 0) {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    } else {
                        res.moveToFirst();
                        String name = res.getString(1);
                        String programCode = res.getString(2);
                        String grade = res.getString(3);
                        String duration = res.getString(4);
                        String fees = res.getString(5);

                        // Set text for ID result TextView
                        idResultTextView.setText("ID: " + id + "\nName: " + name + "\nProgram Code: " + programCode + "\nGrade: " + grade + "\nDuration: " + duration + "\nFees: " + fees);
                        idResultTextView.setVisibility(View.VISIBLE); // Show ID result TextView
                    }
                }
            } else if (radioProgramCode.isChecked()) {
                // Handle search by program code
                int position = searchProgramCodeListView.getCheckedItemPosition();

                if (position == ListView.INVALID_POSITION) {
                    // Display Toasts messages when program code not selected
                    Toast.makeText(getActivity(), "Please select a Program Code", Toast.LENGTH_SHORT).show();
                } else {
                    String programCode = searchProgramCodeListView.getItemAtPosition(position).toString();
                    Cursor res = db.getDataByProgramCode(programCode);

                    // Displays results if available
                    if (res.getCount() == 0) {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    } else {
                        while (res.moveToNext()) {
                            searchResultsList.add(new Grade(
                                    res.getString(0),
                                    res.getString(1),
                                    res.getString(2),
                                    res.getString(3),
                                    res.getString(4),
                                    res.getString(5)
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // Return the view for this fragment
        return view;
    }
}
