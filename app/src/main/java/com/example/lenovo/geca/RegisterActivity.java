package com.example.lenovo.geca;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static View view;
    private static EditText fullName;
    private static EditText emailId;
    private static EditText mobileNumber;
    private static EditText location;
    private static EditText password;
    private static EditText confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private static FirebaseAuth firebaseAuth;
    public static Animation shakeAnimation;
    private ProgressDialog progressDialog;
    public static LinearLayout registerLayout;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LayoutInflater inflater = LayoutInflater.from(RegisterActivity.this); // 1
        view = inflater.inflate(R.layout.activity_register, null);
       registerLayout = (LinearLayout) findViewById(R.id.register_layout);
       fullName = (EditText)findViewById(R.id.fullName);
            emailId = (EditText)findViewById(R.id.userEmailId);
            mobileNumber = (EditText) findViewById(R.id.mobileNumber);
            location = (EditText) findViewById(R.id.location);
            password = (EditText) findViewById(R.id.password);
            confirmPassword = (EditText) findViewById(R.id.confirmPassword);
            signUpButton = (Button) findViewById(R.id.signUpBtn);
            login = (TextView) findViewById(R.id.already_user);
            terms_conditions = (CheckBox)findViewById(R.id.terms_conditions);
        shakeAnimation =AnimationUtils.loadAnimation(this,R.anim.shake);
            signUpButton.setOnClickListener(this);
            login.setOnClickListener(this);
      @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
            try {
                ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
                login.setTextColor(csl);
                terms_conditions.setTextColor(csl);
            } catch (Exception e) {
            }
        Firebase.setAndroidContext(this);
            firebaseAuth = FirebaseAuth.getInstance();
        ref = new Firebase("https://gecaproject.firebaseio.com/");
            progressDialog = new ProgressDialog(this);
        }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
               register();
                break;
                case R.id.already_user:
                Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(it);
                break;
        }
    }
    String email, psw, name, phno, loc,confirmpsw;
    void register() {
        email = emailId.getText().toString().trim();
        name = fullName.getText().toString().trim();
        phno = mobileNumber.getText().toString().trim();
        loc = location.getText().toString().trim();
        psw=password.getText().toString().trim();
        confirmpsw=confirmPassword.getText().toString().trim();
        if (name.equals("") || name.length() == 0 || email.equals("") || email.length() == 0 || phno.equals("") || phno.length() == 0
                ||loc.equals("") || loc.length() == 0
                || psw.equals("") || psw.length() == 0
                || confirmpsw.equals("")
                || confirmpsw.length() == 0){
            registerLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(RegisterActivity.this, view, "All fields are required.");
            return;
        }
        else if (!checkPhone(phno)) {
            mobileNumber.setError("Enter 10 Digit no");
            return;
        }
        else if (!checkEmail(email)) {
            emailId.setError("Your Email Id is Invalid");
            new CustomToast().Show_Toast(this, view, "Your Email Id is Invalid.");
            return;
        }
        else if(!checkPassword(psw)|| !checkPassword(confirmpsw)){
            password.setError(" Enter Strong password having capital,special character,numbers");
            new CustomToast().Show_Toast(this, view,
                    "Enter strong password.");

            return;
        }
        else if(!terms_conditions.isChecked())
        {
            new CustomToast().Show_Toast(this, view, "Please select Terms and Conditions.");
            return;
        }
        else if (!confirmpsw.equals(psw)) {
            new CustomToast().Show_Toast(this, view, "Both password doesn't match.");
            return;
        }
        else
        { Toast.makeText(this, "Do SignUp", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    details ob = new details();
                    ob.setName(name);
                    ob.setEmail(email);
                    ob.setPhone(phno);
                    ob.setLocation(loc);
                    ref.child("details").child(phno).setValue(ob);
                    } else {
                     Toast.makeText(RegisterActivity.this, "Registration Error"+task.getException(), Toast.LENGTH_LONG).show();
                     }
                Intent it1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(it1);
                progressDialog.dismiss();
            }
        });
    }
    Matcher matcher;
    boolean checkEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    boolean checkPassword(String str) {
        Pattern pattern;
        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(psw);
        matcher = pattern.matcher(confirmpsw);
        return matcher.matches();
    }
    boolean checkPhone(String phno) {
        Pattern pattern;
        Matcher matcher;
        String PHONE_PATTERN = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phno);
        return matcher.matches();
    }
}
