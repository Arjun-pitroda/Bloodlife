package com.example.bloodylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etSignupName,etSignupAge,etSignupPhoneNo,etSignupLocation,etSignupEmailId,etSignupPassword,etSignupConfirmPassword;
    Spinner spBloodGroup;
    Button btnSignup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean b = true;
    FirebaseAuth auth;
    FirebaseFirestore fdb;
    DocumentReference dref;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etSignupName=findViewById(R.id.etSignupName);
        etSignupAge=findViewById(R.id.etSignupAge);
        etSignupPhoneNo=findViewById(R.id.etSignupPhoneNo);
        etSignupEmailId=findViewById(R.id.etSignupEmailId);
        etSignupLocation=findViewById(R.id.etSignupLocation);
        etSignupPassword=findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword=findViewById(R.id.etSignupConfirmPassword);

        spBloodGroup=findViewById(R.id.spBloodGroup);

        btnSignup=findViewById(R.id.btnSignup);

        auth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBloodGroup.setAdapter(adapter);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailid = etSignupEmailId.getText().toString();
                final String name = etSignupName.getText().toString();
                final String age = etSignupAge.getText().toString();
                final String bloodgroup = spBloodGroup.getSelectedItem().toString();
                final String phoneno = etSignupPhoneNo.getText().toString();
                final String location = etSignupLocation.getText().toString();
                final String password = etSignupPassword.getText().toString();
                final String confirmpassword = etSignupConfirmPassword.getText().toString();

                if (emailid.matches(emailPattern)) {
                    etSignupEmailId.setError("Enter Valid Email-ID.");
                    etSignupEmailId.setText("");
                    b = false;
                } else {
                    b = true;
                }
                if (name.length() <= 3) {
                    etSignupName.setError("Enter Valid Name.");
                    b = false;
                    etSignupName.setText("");
                } else {
                    b = true;
                }
                if (age.length() == 0 || Integer.parseInt(age) <= 0 || Integer.parseInt(age) > 120) {
                    etSignupAge.setError("Enter Valid Age.");
                    b = false;
                    etSignupAge.setText("");
                } else {
                    b = true;
                }
                if (bloodgroup.compareTo("Blood Group") == 0) {
                    Toast.makeText(SignupActivity.this, "Select a blood group!", Toast.LENGTH_SHORT).show();
                    spBloodGroup.requestFocus();
                    b = false;

                } else {
                    b = true;
                }
                if (phoneno.length() < 10) {
                    etSignupPhoneNo.setError("Enter Valid Phone No.");
                    b = false;
                    etSignupPhoneNo.setText("");
                } else {
                    b = true;
                }
                if (location.length() <= 0) {
                    etSignupLocation.setError("Enter Valid City Name");
                    etSignupLocation.setText("");
                    b = false;
                } else {
                    b = true;
                }
                if (password.length() <= 8) {
                    etSignupPassword.setError("Password should contain atleast 8 characters...");
                    etSignupPassword.setText("");
                    b = false;
                } else {
                    b = true;
                }
                if (password.compareTo(confirmpassword) != 0) {
                    etSignupConfirmPassword.setError("Password don't match!!!");
                    etSignupConfirmPassword.setText("");
                    b = false;
                } else {
                    b = true;
                }
                if (b)
                {
                    auth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                uid = auth.getCurrentUser().getUid();
                                Toast.makeText(SignupActivity.this, "User Created...", Toast.LENGTH_SHORT).show();
                                dref = fdb.collection("Users").document(uid);
                                Map<String, Object> uu = new HashMap<>();
                                uu.put("Email-ID", emailid);
                                uu.put("Name", name);
                                uu.put("Age", age);
                                uu.put("Blood Group", bloodgroup);
                                uu.put("Phone No", phoneno);
                                uu.put("Location", location);
                                uu.put("Password", password);
                                uu.put("Status", false);
                                dref.set(uu).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "New Account Created", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SignupActivity.this, "New Account NOT Created", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Intent i=new Intent(SignupActivity.this,MainActivity.class);
                                startActivity(i);
                            } else {
                                etSignupEmailId.setError("Enter different Email-ID");
                                etSignupEmailId.setText("");
                                Toast.makeText(SignupActivity.this, "Email-ID exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Registration Failed!!!", Toast.LENGTH_SHORT).show();
                }

            }


        });




    }
}
