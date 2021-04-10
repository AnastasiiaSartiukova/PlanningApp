package com.example.planning;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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

public class LogIn extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button log_in;
        TextView register;
        Button read_db;

        GoogleSignInOptions gso;
        SignInButton sign_in_google;

        DatabaseHelper db_helper;

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

                            openLogIn();

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

        //Google interaction
        // If sign in via google pressed
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_in_google = (SignInButton) findViewById(R.id.sign_in_button);

        sign_in_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        //If pressed Registration
        register = findViewById(R.id.cirRegButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                contentValues.put("user_email", email);
                contentValues.put("user_password", password);

                db.insert("users", null, contentValues);

                openRegistration();
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

    @Override
    protected void onStart() {
        super.onStart();

        account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LogIn.this, SecondActivity.class);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void openLogIn(){
        //Intent intent = new Intent(this, LogedInActivity.class);
        //startActivity(intent);
    }

    public void openRegistration(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}