package com.example.bloodylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etEmailid,etPassword;
    TextView tvForgotpassword,tvSignup,tvGmailSignin;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmailid=findViewById(R.id.etEmailId);
        etPassword=findViewById(R.id.etPassword);
        tvForgotpassword=findViewById(R.id.tvForgotPassword);
        tvSignup=findViewById(R.id.tvSignup);
        tvGmailSignin=findViewById(R.id.tvGmailSignin);
        btnLogin=findViewById(R.id.btnLogin);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        if(user!=null)
        {
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String un=etEmailid.getText().toString();
                String pw=etPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(un,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Intent i =new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Failed" +task.getException() , Toast.LENGTH_SHORT).show();
                        }




                    }
                });


            }
        });
        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });




    }
}
