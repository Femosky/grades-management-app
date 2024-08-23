package com.example.appforapp;

public class Grade {
    // Private member variables to store the details of a grade entry
    private final String id;
    private final String name;
    private final String programCode;
    private final String grade;
    private final String duration;
    private final String fees;

    // Constructor to initialize a new Grade object
    public Grade(String id, String name, String programCode, String grade, String duration, String fees) {
        this.id = id;
        this.name = name;
        this.programCode = programCode;
        this.grade = grade;
        this.duration = duration;
        this.fees = fees;
    }

    // Method to Get the ID
    public String getId() {
        return id;
    }

    // Method to Get the Name
    public String getName() {
        return name;
    }

    // Method to Get the Program Code
    public String getProgramCode() {
        return programCode;
    }

    // Method to Get the Grade
    public String getGrade() {
        return grade;
    }

    // Method to Get the Duration
    public String getDuration() {
        return duration;
    }

    // Method to Get the Fees
    public String getFees() {
        return fees;
    }
}
