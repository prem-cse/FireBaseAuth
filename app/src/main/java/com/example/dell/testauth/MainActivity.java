package com.example.dell.testauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   private EditText email;
   private EditText password;
   private Button login;
   private Button SignUp;
   private FirebaseAuth auth;
   private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Intialiaze
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        SignUp = findViewById(R.id.signup);
        progressBar = findViewById(R.id.progessbar);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {

                    Toast.makeText(MainActivity.this, "SuccessFull Sign in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Profile.class));

                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SignIn();
                  // for sign in with email and password
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
        // IMP....
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

   private void SignIn(){

        String Email = email.getText().toString();
        String Password = password.getText().toString();
        // Validations
       if(Email.isEmpty()){
           email.setError("Email is required");
           email.requestFocus();
           return;
       }
       if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
           email.setError("Invalid Email");
           email.requestFocus();
           return;
       }
       if(Password.isEmpty()){
           password.setError("Password is required");
           password.requestFocus();
           return;
       }
       if(Password.length()<8){
           password.setError("Minimum length should be 8");
           password.requestFocus();
           return;
       }
       // if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
         //   Toast.makeText(MainActivity.this,"Please fill the fields",Toast.LENGTH_SHORT).show();
        //}else {
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (!task.isSuccessful()) {
                        // if credentials are wrong
                        Print(task.getException().getMessage());
                       // Toast.makeText(MainActivity.this, "Sign in Problem", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(MainActivity.this, Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        */

                    }
                }
            });
        //```}
   }
    private void Print(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
    }

}
