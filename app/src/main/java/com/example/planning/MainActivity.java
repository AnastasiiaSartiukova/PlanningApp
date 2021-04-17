package com.example.planning;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private String name;
    private BottomNavigationItemView create_project;
    private BottomNavigationItemView shared_project;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getStringExtra("Name") == null){
            name = "Anastasia Srtiukova";
        }
        else{
            name = getIntent().getStringExtra("Name");
        }

        //If setting button pressed
        settings = findViewById(R.id.settings_button);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openSettings(name); }
        });

        //If pressed Create new project
        create_project = findViewById(R.id.add_project);

        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });



        //If pressed shared project button
        shared_project = findViewById(R.id.shared_project);

        shared_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openLogIn();}
        });

    }

    public void createProject(){
        Intent intent = new Intent(this, CreateProjectActivity.class);
        startActivity(intent);
    }

    public void openLogIn(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void openSettings(String name){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("Name", name);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}