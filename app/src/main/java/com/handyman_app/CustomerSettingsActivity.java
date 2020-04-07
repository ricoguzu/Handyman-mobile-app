package com.handyman_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CustomerSettingsActivity extends AppCompatActivity {
    //import buttons and Edit  text
    private EditText mNameF, mPhoneF, mIdF, mAddressF, mEmailF;

    private Button   mBack, mConfirm;

    //create variable
    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private String mName;
    private String mPhone;
    private String mId;
    private String mAddress;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_settings);

        //find buttons and text by id
        mNameF = (EditText) findViewById(R.id.name);
        mPhoneF = (EditText) findViewById(R.id.phone);
        mIdF = (EditText) findViewById(R.id.id);
        mAddressF = (EditText) findViewById(R.id.address);
        mEmailF = (EditText) findViewById(R.id.email);

        mBack = (Button) findViewById(R.id.back);
        mConfirm = (Button) findViewById(R.id.confirm);

        //give value to the variables
        mAuth  = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        //pass the user_id to the dbReference
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);

        //call fxn
        getUserInfo();

        //onclick listener
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }
    //get user info first fxn
    private void getUserInfo(){
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        mName = map.get("name").toString();
                        mNameF.setText(mName);
                    }
                    if(map.get("phone")!=null){
                        mPhone = map.get("phone").toString();
                        mPhoneF.setText(mPhone);
                    }
                    if(map.get("id")!=null){
                        mId = map.get("id").toString();
                        mIdF.setText(mId);
                    }
                    if(map.get("address")!=null){
                        mAddress = map.get("address").toString();
                        mAddressF.setText(mAddress);
                    }
                    if(map.get("email")!=null){
                        mEmail = map.get("email").toString();
                        mEmailF.setText(mEmail);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

      //method for saving userInformation
    private void saveUserInformation() {
        mName    = mNameF.getText().toString();
        mPhone   = mPhoneF.getText().toString();
        mId      = mIdF.getText().toString();
        mAddress = mAddressF.getText().toString();
        mEmail   = mEmailF.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("phone",mPhone);
        userInfo.put("id",mId);
        userInfo.put("address",mAddress);
        userInfo.put("email",mEmail);
        mCustomerDatabase.updateChildren(userInfo);

        finish();
    }
}
