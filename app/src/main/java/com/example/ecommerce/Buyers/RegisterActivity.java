package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button AccountButton;
    private EditText inputName,inputPassword,inputNumber;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AccountButton=(Button) findViewById(R.id.registerBtn);
        inputName=(EditText)findViewById(R.id.register_username);
        inputPassword=(EditText)findViewById(R.id.register_password);
        inputNumber=(EditText)findViewById(R.id.register_phone_number);
        loadingBar= new ProgressDialog(  this);


        AccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();

            }
        });
    }
    //the function for registering an account
    public void CreateAccount(){

        String name=inputName.getText().toString();
        String phone=inputNumber.getText().toString();
        String  password=inputPassword.getText().toString();
        if(TextUtils.isEmpty(name)){

            Toast.makeText(getApplicationContext(),"Please enter username......",Toast.LENGTH_SHORT).show();

        }

        else if(TextUtils.isEmpty(phone)){


            Toast.makeText(getApplicationContext(),"Please enter phone number....",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(getApplicationContext(),"please enter password....",Toast.LENGTH_SHORT).show();

        }
        else
            {
             loadingBar.setTitle("Create Account");
             loadingBar.setMessage("Please wait as you get registered....");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();
                //Toast.makeText(getApplicationContext(),"registering you...",Toast.LENGTH_SHORT).show();
             ValidatephoneNumber(name, phone, password);



        }
    }

  /////function to verify a phone number
        private void ValidatephoneNumber(final String name, final String phone, final String password)
        {
            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (!(dataSnapshot.child("Users").child(phone).exists()))
                    {
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("phone", phone);
                        userdataMap.put("password", password);
                        userdataMap.put("name", name);

                        RootRef.child("Users").child(phone).updateChildren(userdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {

                                            Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"DATABASE ERROR...",Toast.LENGTH_SHORT).show();

                }
            });

}
    }

