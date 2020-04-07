package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Admin.AdminHomeActivity;
import com.example.ecommerce.BasicActivity;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.example.ecommerce.Sellers.SellerHomeActivity;
import com.example.ecommerce.Sellers.SellerRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button loginButton,joinButton;
    private ProgressDialog loadingBar;
    private TextView   sellerBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinButton= (Button) findViewById(R.id.joinButton);
        loginButton= (Button) findViewById(R.id.loginButton);
        sellerBegin= (TextView) findViewById(R.id.seller_begin);
        Paper.init(this);
        loadingBar= new ProgressDialog(  this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
      sellerBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SellerRegistrationActivity.class);
                startActivity(intent);

            }
        });

        //the button to open register page
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, BasicActivity.class);
                startActivity(intent);

            }
        });

       String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPassword=Paper.book().read(Prevalent.UserPassword);
        if(UserPhoneKey!="" && UserPassword!="")
        {
             if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassword)){

                AllowAccess(UserPhoneKey,UserPassword);
                 loadingBar.setTitle("Already Logged In");
                 loadingBar.setMessage("Please wait ..");
                 loadingBar.setCanceledOnTouchOutside(false);
                 loadingBar.show();
             }

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null)
        {

            Intent intent = new Intent(MainActivity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){

                    Users usersData=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if(usersData.getPhone().equals(phone)){
                        //here it checks whether the password is correct, if not they
                        if(usersData.getPassword().equals(password)){


                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            Prevalent.currentOnlineUser=usersData;
                            startActivity(intent);
                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Wrong Password Entered", Toast.LENGTH_SHORT).show();


                        }

                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "This " + phone + "  number do not exist.", Toast.LENGTH_SHORT).show();

                    Toast.makeText(MainActivity.this, "You need to create a new Account", Toast.LENGTH_SHORT).show();
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
