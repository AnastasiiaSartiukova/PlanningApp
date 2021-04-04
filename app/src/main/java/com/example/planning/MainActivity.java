package com.example.planning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity{


    private ImageButton project_list;
    private ImageButton create_project;
    private ImageButton shared_project;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //If pressed Create new project
        create_project = (ImageButton) findViewById(R.id.add_project);

        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });



        //If pressed Project list button
        project_list = (ImageButton) findViewById(R.id.project_list);

        project_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProjectList();
            }
        });



        //If pressed shared project button
        shared_project = (ImageButton) findViewById(R.id.shared_project);

        shared_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openLogIn();}
        });

    }

    public void createProject(){
        Intent intent = new Intent(this, CreateProjectActivity.class);
        startActivity(intent);
    }

    public void openProjectList(){
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
    }

    public void openLogIn(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}