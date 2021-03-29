package com.MohamedKohail.flashchatnewfirebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {


    // UI references.

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // to Grab an instance of FirebaseAuth

        mAuth = FirebaseAuth.getInstance();


    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v) {

        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.MohamedKohail.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptLogin() {


        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.equals("") || password.equals("")) return;

        Toast.makeText(this, "Loging...", Toast.LENGTH_LONG).show();


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("chat", "signingWithEmail onComplete: " + task.isSuccessful());


                if (!task.isSuccessful()) {

                    Log.d("chat", "signing in failed: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                } else {

                    Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
                    finish();
                    startActivity(intent);

                }


            }
        });


    }


    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this).setTitle("Oops!").setMessage(message).setPositiveButton(android.R.string.ok, null).setIcon(android.R.drawable.ic_dialog_alert).show();

    }


}