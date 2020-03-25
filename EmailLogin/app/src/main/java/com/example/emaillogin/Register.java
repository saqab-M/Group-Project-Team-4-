package com.example.emaillogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    //Create variables for the text inputs and buttons on the register page
    EditText mFullName,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connect variables to the resources on the page
        mFullName = findViewById(R.id.rFullName);
        mEmail = findViewById(R.id.rEmail);
        mPassword = findViewById(R.id.rPassword);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.toLogin);
        progressBar = findViewById(R.id.registerProgressBar);

        //Get the current instance from Firebase so we can perform the various operations
        fAuth = FirebaseAuth.getInstance();

        //Get the current user
        currentUser = fAuth.getCurrentUser();

        //Check if the user is already logged in
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        //When the register button is clicked
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the email and password from the text input fields
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //Check if the input for email is empty
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Enter an email");
                    return;
                }
                //Check if the input for password is empty
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Enter a password");
                    return;
                }
                //Check if the length of the password is greater than 6 characters
                if (password.length() < 6){
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }
                //Make the progress bar visible
                progressBar.setVisibility(View.VISIBLE);

                //Register the user in Firebase

                //Attempt to register user with email and password and use event listener to determine if the registration was successful or not
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the registration is successful
                        if (task.isSuccessful()){
                            //Show message to the user that the account has been created
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            //Send user to the main activity
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        //If the registration is unsuccessful
                        else{
                            //Show error message
                            Toast.makeText(Register.this, "Error in registration " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);


                        }
                    }
                });



            }
        });

        //When the login text field is clicked
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send user to the main activity
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
