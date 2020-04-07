package com.example.ecommerce.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {
    private Button loginSeller,registerSeller;
    private EditText nameInput,phoneInput,emailInput,passwordInput,addressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        loginSeller= (Button) findViewById(R.id.loginSeller);
        registerSeller= (Button) findViewById(R.id.registerSeller);
        nameInput= (EditText) findViewById(R.id.name);
        phoneInput= (EditText) findViewById(R.id.phone);
        emailInput= (EditText) findViewById(R.id.email);
        passwordInput= (EditText) findViewById(R.id.password);
        addressInput= (EditText) findViewById(R.id.address);
        loadingBar= new ProgressDialog(  this);
        mAuth=FirebaseAuth.getInstance();


        registerSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerSeller();

            }
        });
        loginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void registerSeller() {
          final String name=nameInput.getText().toString();
        final String phone=phoneInput.getText().toString();
        final String email=emailInput.getText().toString();
        final String password=passwordInput.getText().toString();
        final String address=addressInput.getText().toString();
       if(!name.equals("") &&  !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")){

           loadingBar.setTitle("Create  Seller Account");
           loadingBar.setMessage("Please wait as you get registered....");
           loadingBar.setCanceledOnTouchOutside(false);
           loadingBar.show();




           mAuth.createUserWithEmailAndPassword(email,password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SellerRegistrationActivity.this,
                                            "you registered Successfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    /*final DatabaseReference rootRef;
                                    rootRef= FirebaseDatabase.getInstance().getReference();
                                    String sid=mAuth.getCurrentUser().getUid();
                                    HashMap<String, Object >  sellerMap= new HashMap<>();
                                    sellerMap.put("sid",sid);
                                    sellerMap.put("name",name);
                                    sellerMap.put("phone",phone);
                                    sellerMap.put("email",email);
                                    //sellerMap.put("password",password);
                                    sellerMap.put("address",address);
                                    rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    loadingBar.dismiss();
                                                    Toast.makeText(SellerRegistrationActivity.this,
                                                            "you registered Successfully.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });*/
                                }
                       }
                   });


       }

       else {
           Toast.makeText(this, "Please complete the registration form", Toast.LENGTH_SHORT).show();


       }

    }
}
