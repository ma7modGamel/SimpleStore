package com.example.simplestore.LogInUsers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.simplestore.R;



public class RegisterActivity extends AppCompatActivity {

    private static final String TAG="RegisterActivity";
    TextView viewLogin;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewLogin=findViewById(R.id.id_tv_gotoRegActivity);
        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
