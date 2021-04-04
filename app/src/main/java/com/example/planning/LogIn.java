package com.example.planning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    private Button log_in;
    private Button register;
    private Button read_db;
    private EditText etEmail;
    private EditText etPassword;

    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);

        db_helper = new DatabaseHelper(this);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = db_helper.getWritableDatabase();

        //If pressed LogIn
        log_in = (Button) findViewById(R.id.cirLoginButton);

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is_logged = false;

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Cursor cursor = db.rawQuery("SELECT user_email, user_password FROM users", null);

                if(cursor.moveToFirst()){

                    int email_index = cursor.getColumnIndex("user_email");
                    int password_index = cursor.getColumnIndex("user_password");

                    do{
                        String current_email = cursor.getString(email_index);
                        String current_password = cursor.getString(password_index);

                        if(current_email.equals(email) && current_password.equals(password)){
                            Log.d("mLog", "Log in success");
                            is_logged = true;
                            break;
                        }

                    } while(cursor.moveToNext());

                    if(!is_logged) {
                        Log.d("mLog", "Log in failed");
                    }
                }
                else{
                    Log.d("mLog", "0 rows");
                }

                cursor.close();
            }
        });

        //If pressed Registration
        register = (Button) findViewById(R.id.cirRegButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                contentValues.put("user_email", email);
                contentValues.put("user_password", password);

                db.insert("users", null, contentValues);


            }
        });

        //If pressed Read
        read_db = (Button) findViewById(R.id.cirReadButton);

        read_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = db.query("users", null, null, null, null, null, null);

                if(cursor.moveToFirst()){

                    int id_index = cursor.getColumnIndex("userID");
                    int email_index = cursor.getColumnIndex("user_email");
                    int password_index = cursor.getColumnIndex("user_password");

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(id_index)
                                + ", email = " + cursor.getString(email_index)
                                + ", password = " + cursor.getString(password_index));
                    } while(cursor.moveToNext());
                }

                else{
                    Log.d("mLog", "0 rows");
                }

                cursor.close();

            }
        });


    }
}