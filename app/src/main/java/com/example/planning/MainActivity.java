package com.example.planning;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private String name;
    private boolean is_logged;
    private BottomNavigationItemView create_project;
    private BottomNavigationItemView shared_project;

    private LinearLayout parent;
    private View child;
    private DatabaseHelper db_helper;

    private TextView tName;
    private TextView tDate;
    private TextView tCost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = locateUserName();
        is_logged = locateLogged(name);

        //Adding all project to layout where name in session = name as project creator
        add_all_projects_to_layout(name);

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
            public void onClick(View v) {

                if(!is_logged) {
                    openLogIn();
                }
                else {
                    openSettings(name);
                }
            }
        });

    }

    private Boolean locateLogged(String name){
        return !name.equals("Anonimus");
    }

    private String locateUserName(){

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null){
            return acct.getDisplayName();
        }

        if(getIntent().getStringExtra("Name") == null){
            return "Anonimus";
        }
        else{
            return getIntent().getStringExtra("Name");
        }
    }


    public void createProject(){
        Intent intent = new Intent(this, CreateProjectActivity.class);
        intent.putExtra("Name", name);
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
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void add_all_projects_to_layout(String name){
        parent = findViewById(R.id.project_list);

        db_helper = new DatabaseHelper(this);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = db_helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT pName, pDate, pCreator, pCost FROM projects", null);

        if(cursor.moveToFirst()){

            int name_index = cursor.getColumnIndex("pName");
            int date_index = cursor.getColumnIndex("pDate");
            int creator_index = cursor.getColumnIndex("pCreator");
            int cost_index = cursor.getColumnIndex("pCost");

            do{

                String current_creator = cursor.getString(creator_index);

                if(!current_creator.equals(name)){
                    continue;
                }

                String current_name = cursor.getString(name_index);
                String current_date = cursor.getString(date_index);
                String current_cost = cursor.getString(cost_index);


                child = getLayoutInflater().inflate(R.layout.card_profile, null);

                tName = child.findViewById(R.id.project_name);
                tName.setText(current_name);

                tDate = child.findViewById(R.id.project_date);

                tDate.setText("Date: " + current_date);

                tCost = child.findViewById(R.id.project_cost);
                tCost.setText("Cost: " + current_cost + " UAH");

                parent.addView(child);


            } while(cursor.moveToNext());

        }
        else{
            Log.d("mLog", "0 rows");
        }

        cursor.close();
    }

}