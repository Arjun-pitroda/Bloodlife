package com.example.bloodylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    EditText etForgotEmailId;
    Button btnResetPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        etForgotEmailId=findViewById(R.id.etForgotEmailId);
        btnResetPassword=findViewById(R.id.btnLogin);
        firebaseAuth=FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un=etForgotEmailId.getText().toString();
                firebaseAuth.sendPasswordResetEmail(un).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent i =new Intent(ForgotActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(ForgotActivity.this, "" +task.getException() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}
