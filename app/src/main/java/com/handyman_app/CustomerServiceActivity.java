package com.handyman_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerServiceActivity extends AppCompatActivity {
    private Button mLogout,mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        mLogout   =   (Button) findViewById(R.id.logout);
        mSettings = (Button) findViewById(R.id.settings);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerServiceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerServiceActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

}
