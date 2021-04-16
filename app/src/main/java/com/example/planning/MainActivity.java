package com.example.planning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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

    private BottomNavigationItemView create_project;
    private BottomNavigationItemView shared_project;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}