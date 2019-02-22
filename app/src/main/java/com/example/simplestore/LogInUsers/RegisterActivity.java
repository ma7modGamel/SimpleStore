package com.example.simplestore.LogInUsers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplestore.MainActivity;
import com.example.simplestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG="RegisterActivity";
    TextView viewLogin;
    Button btnRegister;
    private FirebaseAuth mAuth;
    EditText email,password,fullName,Phone;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.et_emailReg);
        password=findViewById(R.id.et_passwordReg);
        fullName=findViewById(R.id.et_fullname);
        Phone=findViewById(R.id.et_phone);
        btnRegister=findViewById(R.id.btnRegisterNow);
        viewLogin=findViewById(R.id.id_tv_gotoRegActivity);
        mAuth = FirebaseAuth.getInstance();

        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString().trim()) || (TextUtils.isEmpty(password.getText().toString().trim()))||TextUtils.isEmpty(fullName.getText().toString().trim()) || (TextUtils.isEmpty(Phone.getText().toString().trim()))) {
                    Toast.makeText(RegisterActivity.this, "please enter right data .. ", Toast.LENGTH_SHORT).show();
                } else {

                    String uMail = email.getText().toString().trim();
                    if (checkEmail(uMail) == true) {
                        if(isValidPhoneNumber((Phone.getText().toString().trim()))==true) {

                        mAuth.createUserWithEmailAndPassword(uMail, password.getText().toString().trim())
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                                            intent.putExtra("username", email.getText().toString().trim());
                                            intent.putExtra("password", password.getText().toString().trim());
                                            startActivity(intent);
                                            finish();


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                        // ...
                                    }
                                });
                        }else{
                            Toast.makeText(RegisterActivity.this, "phone is Valid .. ", Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "E-Mail is Valid .. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }
    public static boolean checkEmail(String email) {

        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
