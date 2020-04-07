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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Admin.AdminCategoryActivity;
import com.example.ecommerce.Admin.AdminManageActivity;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText inputNumber, inputPassword;
    private Button LoginButton;
    private TextView registerLink,AdminLink,NotAdminLink,forgetPasswordLink;
    private ProgressDialog loadingBar;
    private  String parentDBName="Users";
    private CheckBox  chBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton=(Button) findViewById(R.id.loginBtn);
        inputNumber=(EditText) findViewById(R.id.login_phone_number);
        inputPassword=(EditText) findViewById(R.id.login_password);
        registerLink=(TextView) findViewById(R.id.register_link);
        AdminLink=(TextView) findViewById(R.id.admin_link);
        NotAdminLink=(TextView) findViewById(R.id.not_admin_link);
        forgetPasswordLink=(TextView) findViewById(R.id.password_reset);
        loadingBar= new ProgressDialog(  this);
        chBox=(CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);
        //the button for log in when clicked

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //when admin link is clicked
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("LOG IN ADMIN");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDBName="Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("LOG IN USER");
                NotAdminLink.setVisibility(View.INVISIBLE);
                AdminLink.setVisibility(View.VISIBLE);
                parentDBName="Users";
            }
        });
        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });



    }

    private void loginUser() {

        String phone=inputNumber.getText().toString();
        String  password=inputPassword.getText().toString();
        if(TextUtils.isEmpty(phone)){


            Toast.makeText(getApplicationContext(),"Please enter phone number....",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(getApplicationContext(),"please enter password....",Toast.LENGTH_SHORT).show();

        }

        else
        {
             loadingBar.setTitle("Logging In");
             loadingBar.setMessage("Please wait while we are checking the credentials..");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();
           
             AllowAccessToAccount(phone, password);




        }

    }

    private void AllowAccessToAccount(final String phone, final String password) {
         //checking the check box returning a boolean
        if(chBox.isChecked())
        {
            //when the user logs in
           Paper.book().write(Prevalent.UserPhoneKey, phone);
           Paper.book().write(Prevalent.UserPassword,password);

        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBName).child(phone).exists()){

                 Users usersData=dataSnapshot.child(parentDBName).child(phone).getValue(Users.class);
                 if(usersData.getPhone().equals(phone)){
                        //here it checks whether the password is correct, if not they
                     if(usersData.getPassword().equals(password)){

                         if(parentDBName.equals("Admins"))
                         {
                             Toast.makeText(LoginActivity.this, "Welcome Admin, You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                             loadingBar.dismiss();
                             Intent intent = new Intent(LoginActivity.this, AdminManageActivity.class);
                             startActivity(intent);

                         }
                         else if(parentDBName.equals("Users"))
                         {
                             Toast.makeText(LoginActivity.this, "Welcome User, you are Logged In Successfully", Toast.LENGTH_SHORT).show();
                             loadingBar.dismiss();
                             Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                             Prevalent.currentOnlineUser=usersData;
                             startActivity(intent);

                         }



                     }
                     else{
                         loadingBar.dismiss();
                         Toast.makeText(LoginActivity.this, "Wrong Password Entered", Toast.LENGTH_SHORT).show();


                     }

                 }
                }
                else {
                    Toast.makeText(LoginActivity.this, "This " + phone + "  number do not exist.", Toast.LENGTH_SHORT).show();

                    Toast.makeText(LoginActivity.this, "You can create an  Account by clicking Create Account Link", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"DATABASE ERROR...",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
