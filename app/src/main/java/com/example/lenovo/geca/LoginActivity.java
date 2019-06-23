package com.example.lenovo.geca;
import com.example.lenovo.geca.CustomToast;

import android.app.FragmentContainer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;

import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static View view;

    private static EditText emailId, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    public static LinearLayout loginLayout;
    public static Animation shakeAnimation;
    public ProgressDialog progressDialog;
    public FirebaseAuth firebaseAuth;
    String email1;
    Firebase ref;
    Query q;
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         progressDialog = new ProgressDialog(this);
        emailId = (EditText) findViewById(R.id.login_emailid);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
       // forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (TextView) findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        shakeAnimation =AnimationUtils.loadAnimation(this,R.anim.shake);
        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
         view = inflater.inflate(R.layout.activity_login, null);
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        Firebase.setAndroidContext(this);
        firebaseAuth=FirebaseAuth.getInstance();
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);
                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                        }
                    }
                });
    }
    void login() {
        final String email=emailId.getText().toString();
        String pass=password.getText().toString();
        if (email.equals("") || email.length() == 0 || pass.equals("") || pass.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);

            new CustomToast().Show_Toast(LoginActivity.this, view,
                    "Enter both credentials.");

            return;
        }
        progressDialog.setMessage("Login please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Toast.makeText(LoginActivity.this, "processing", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()) {

                    TEST.email=email;
                    select();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                login();
                break;
               case R.id.createAccount:
                Intent it=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(it);
                break;
        }
    }
    void select() {
        try {
           email1 = TEST.email;
            ref = new Firebase("https://gecaproject.firebaseio.com/details");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record
                            try {
                                   details p1 = sn.getValue(details.class);
                                    if (p1.getEmail().equals(email1)) {
                                        TEST.phone = p1.getPhone();
                                        TEST.name=p1.getName();
                                        TEST.cimg=p1.getImg();
                                    }
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }
                    }
                    Intent it=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(it);
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
}
