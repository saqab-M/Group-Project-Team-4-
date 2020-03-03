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

public class Login extends AppCompatActivity {
    //Create variables for the text inputs and buttons on the login page
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mRegisterBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connect variables to the resources on the page
        mEmail = findViewById(R.id.lEmail);
        mPassword = findViewById(R.id.lPassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterBtn = findViewById(R.id.toRegister);
        progressBar = findViewById(R.id.loginProgressBar);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                //Authenticate the user

                  fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          //If the registration is successful
                          if (task.isSuccessful()){
                              //Show message to the user that they've successfully logged in
                              Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                              //Send user to the main activity
                              startActivity(new Intent(getApplicationContext(),MainActivity.class));
                          }
                          //If the registration is unsuccessful
                          else{
                              //Show error message
                              Toast.makeText(Login.this, "Error in login " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                              progressBar.setVisibility(View.GONE);


                          }
                      }
                  });

            }
        });

        //When the user clicks the register text field
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send user to the main activity
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}
