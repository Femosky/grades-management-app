package com.example.appforapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database and table names
    private static final String dbName = "Grades.db";
    private static final String TABLE_NAME = "grades_table";

    // Column names
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "PROGRAM_CODE";
    private static final String COL4 = "GRADE";
    private static final String COL5 = "DURATION";
    private static final String COL6 = "FEES";
    private Context context;

    // Constructor to initialize the database helper
    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
        this.context = context;
    }

    // Method to create the table in the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PROGRAM_CODE TEXT, GRADE TEXT, DURATION TEXT, FEES TEXT)");
    }

    // Method to upgrade the database (drop and recreate the table)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to insert a new record into the database
    public boolean insertData(String name, String programCode, String grade, String duration, String fees) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Putting data into ContentValues
        contentValues.put(COL2, name);
        contentValues.put(COL3, programCode);
        contentValues.put(COL4, grade);
        contentValues.put(COL5, duration);
        contentValues.put(COL6, fees);

        // Inserting the data and checking if insertion was successful
        long result = db.insert(TABLE_NAME, null, contentValues);

        // Return true if data is inserted, false otherwise
        return result != -1;
    }

    // Method to get all records from the database
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Method to get a record by ID
    public Cursor getDataById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", new String[]{id});
    }

    // Method to get records by program code
    public Cursor getDataByProgramCode(String programCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE PROGRAM_CODE = ?", new String[]{programCode});
    }
}
