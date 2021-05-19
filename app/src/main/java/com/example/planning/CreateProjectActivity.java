package com.example.planning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class CreateProjectActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button saveProject;
    private EditText projectDate;
    private String name;

    private Button addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        DatabaseHelper db_helper;

        if(getIntent().getStringExtra("Name") == null){
            name = "Anonimus";
        }
        else{
            name = getIntent().getStringExtra("Name");
        }

        db_helper = new DatabaseHelper(this);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = db_helper.getWritableDatabase();

        this.projectDate = (EditText) this.findViewById(R.id.project_date);
        this.saveProject = (Button) this.findViewById(R.id.save_project);
        this.datePicker = (DatePicker) this.findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.datePicker.init( year, month , day , new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePickerChange(  datePicker,   year,   month,   dayOfMonth);
            }
        });

        this.saveProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tip = findViewById(R.id.successAdded);

                EditText eName = findViewById(R.id.project_name);
                EditText eDate = findViewById(R.id.project_date);
                EditText eCost = findViewById(R.id.project_cost);

                String pName = eName.getText().toString();
                String date = eDate.getText().toString();
                String cost = eCost.getText().toString();
                String cName = name;
                
                contentValues.put("pName", pName);
                contentValues.put("pDate", date);
                contentValues.put("pCost", cost);
                contentValues.put("pCreator", cName);

                db.insert("projects", null, contentValues);

                tip.setText("Successful Added");
                tip.setVisibility(View.VISIBLE);

            }
        });
    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.projectDate.setText(dayOfMonth +"-" + (month + 1) + "-" + year);
    }

}