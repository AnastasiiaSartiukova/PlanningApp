package com.example.planning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regMail;
    private EditText regPass;
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView back;
        Button reg;
        DatabaseHelper db_helper;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db_helper = new DatabaseHelper(this);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = db_helper.getWritableDatabase();


        //If register pressed
        reg = findViewById(R.id.reg_button);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is_reg = true;

                regMail = findViewById(R.id.reg_mail);
                regPass = findViewById(R.id.reg_pass);

                String email = regMail.getText().toString();
                String password = regPass.getText().toString();

                Cursor cursor = db.rawQuery("SELECT user_email FROM users", null);

                if(cursor.moveToFirst()){

                    int email_index = cursor.getColumnIndex("user_email");

                    do{
                        String current_email = cursor.getString(email_index);

                        if(current_email.equals(email)){

                            is_reg = false;

                            tip = findViewById(R.id.successReg);
                            tip.setText("Email alredy registered");
                            tip.setVisibility(View.VISIBLE);

                            break;
                        }

                    } while(cursor.moveToNext());

                    if(is_reg) {

                        contentValues.put("user_email", email);
                        contentValues.put("user_password", password);

                        db.insert("users", null, contentValues);

                        tip = findViewById(R.id.successReg);
                        tip.setText("Successful registration");
                        tip.setVisibility(View.VISIBLE);

                    }
                    else{
                        Log.d("Log.m", "Already reg");
                    }
                }
                else{
                    Log.d("mLog", "0 rows");
                }

                cursor.close();

            }
        });

        back = findViewById(R.id.back_logIn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToLogIn();

            }
        });
    }

    void backToLogIn(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}