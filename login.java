package com.example.hackathonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jano_ki.R;
import com.example.jano_ki.SignupPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button Login, Register;
    private EditText Name;
    private EditText Password;

    private TextView ForgetPass;
    public static SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.loginPage_btnLogin);
        Name = findViewById(R.id.loginPage_etEmail); // email
        Password = findViewById(R.id.LoginPage_etPassword);
        Register = findViewById(R.id.loginPage_tvSignup);
        ForgetPass = findViewById(R.id.loginPage_tvForgotPassword);


        sp = getSharedPreferences("login", MODE_PRIVATE);

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation zoomout = AnimationUtils.loadAnimation(LoginPage.this, R.anim.splash_screen);
                lp_llLogin.setAnimation(zoomout);
            }
        });*/
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        final String name1 = Name.getText().toString();
        final String pass1 = Password.getText().toString();


        if (sp.getBoolean("logged", false)) {
            startActivity(new Intent(login.this, Fun_page.class));
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Name.getText().toString().equals("") || Password.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Login Information is empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    validate(Name.getText().toString(), Password.getText().toString());
                    sp.edit().putBoolean("logged", true).apply();

                }
            }
        });


        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(login.this, ForgetPass.class));
                finish();

            }
        });


        //     LoginPage_tvForgotPassword = findViewById(R.id.loginPage_tvSignup);
        //    LoginPage_tvForgotPassword.setOnClickListener(new View.OnClickListener()
        //    {
        //       @Override
        //       public void onClick(View v) {
        //          Intent i= new Intent(getApplicationContext(),SignUp.class);
        //          startActivity(i);
        //        /*setContentView(R.layout.activity_sign_up);*/
        //        finish();
        //        }
        //     });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(login.this, SignupPage.class));
                finish();

            }
        });
    }

    private void validate(String userName, String userPassword) {


        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                } else {
                    Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(login.this, Fun_page.class));
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

