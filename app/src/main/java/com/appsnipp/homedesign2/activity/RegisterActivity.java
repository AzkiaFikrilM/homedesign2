package com.appsnipp.homedesign2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.homedesign2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText txt_register_username, txt_register_email, txt_register_password;
    TextInputLayout txt_layout_username, layout_register_email, txt_layout_password;
    Button btn_register;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String name, email, password;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        txt_layout_password = findViewById(R.id.register_layout_password);
        txt_register_password = findViewById(R.id.txt_register_password);

        txt_layout_username = findViewById(R.id.register_layout_name);
        txt_register_username = findViewById(R.id.txt_register_name);

        layout_register_email = findViewById(R.id.register_layout_username);
        txt_register_email = findViewById(R.id.txt_register_username);

        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);

        txt_register_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    registerUser();
                }
                return false;
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        name = txt_register_username.getText().toString();
        email = txt_register_email.getText().toString();
        password = txt_register_password.getText().toString();
        if (name.trim().isEmpty()) {
            txt_layout_username.setError("Invalid Name");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isEmpty()) {
            txt_layout_username.setError("Invalid Email");
            txt_layout_password.setError("Invalid Password");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !password.isEmpty()) {
            txt_layout_username.setError("Invalid Email");
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isEmpty()) {
            txt_layout_password.setError("Invalid Password");
        } else if (password.length() < 6) {
            txt_layout_password.setError("Password length must be 6 characters or more");
        } else {
            txt_layout_username.setError(null);
            txt_layout_password.setError(null);
            progressDialog.setMessage("We're registering you...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Uri uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/drawable/restaurant");

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(txt_register_username.getText().toString())
                                        .setPhotoUri(uri).build();

                                user.updateProfile(profileUpdates);

                                Toast.makeText(getApplicationContext(), "You're registered!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sorry, we're not able to registered you", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                            txt_register_password.setText("");
                            txt_register_username.setText("");
                            txt_register_email.setText("");
                            txt_register_username.setFocusable(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}