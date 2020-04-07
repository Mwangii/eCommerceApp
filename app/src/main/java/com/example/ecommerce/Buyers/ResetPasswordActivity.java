package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {
    private String check = "";
    private TextView pageTitle,titleQuestions;
    private EditText phoneNumber,questionOne,questionTwo;
    private Button verifyButton;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        check = getIntent().getStringExtra("check");
        pageTitle=(TextView)findViewById(R.id.page_title);
        titleQuestions=(TextView)findViewById(R.id.title_questions);
        phoneNumber=(EditText) findViewById(R.id.find_phone_number);
        questionOne=(EditText) findViewById(R.id.question_1);
        questionTwo=(EditText) findViewById(R.id.question_2);
        verifyButton=(Button) findViewById(R.id.verify_btn);

    }



    @Override
    protected void onStart()
    {
        super.onStart();
        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings"))
        {
                pageTitle.setText("Set Questions");
            titleQuestions.setText("Hello,Please answer the Test Questions");
                verifyButton.setText("Set");

            displayPreviousAnswers();
                verifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         setAnswers();

                    }
                });
        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });
        }
    }

    private void verifyUser() {

      final String phone=phoneNumber.getText().toString();
        final String answer1=questionOne.getText().toString().toLowerCase();
        final String answer2=questionTwo.getText().toString().toLowerCase();
        if(!phone.equals("") && !answer1.equals("") && !answer2.equals(""))
        {


            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").
                    child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        String mPhone=dataSnapshot.child("phone").getValue().toString();
                        if(dataSnapshot.hasChild("Security Questions"))
                        {
                            String ans1=dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2=dataSnapshot.child("Security Questions").child("answer2").getValue().toString();
                            if(!ans1.equals(answer1)){

                                Toast.makeText(ResetPasswordActivity.this, "You entered an incorrect First answer", Toast.LENGTH_SHORT).show();
                            }
                            else  if(!ans2.equals(answer2)){

                                Toast.makeText(ResetPasswordActivity.this, "You entered an incorrect Second answer", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                AlertDialog.Builder builder=new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");
                                final EditText newPassword=new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write your password here");
                                builder.setView(newPassword);
                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(!newPassword.getText().toString().equals("")){

                                            ref.child("password").setValue(newPassword.getText().toString()).addOnCompleteListener(new
                                                                                                                                           OnCompleteListener<Void>() {
                                                                                                                                               @Override
                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                   if (task.isSuccessful()) {

                                                                                                                                                       Toast.makeText(ResetPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                                                                                                                       Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                                                                                                       startActivity(intent);

                                                                                                                                                   }

                                                                                                                                               }
                                                                                                                                           });
                                        }

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
;


                                    }
                                });
                                builder.show();
                            }

                        }

                        else{

                            Toast.makeText(ResetPasswordActivity.this, "You have not set the security question",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{

                        Toast.makeText(ResetPasswordActivity.this, "This  Phone number entered do not exist", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

           else{

            Toast.makeText(ResetPasswordActivity.this, "please complete the form", Toast.LENGTH_SHORT).show();

        }


    }

    private  void setAnswers()
    {
        String answer1=questionOne.getText().toString().toLowerCase();
        String answer2=questionTwo.getText().toString().toLowerCase();

        if(answer1.equals(""))
        {

            Toast.makeText(ResetPasswordActivity.this, "Please answer first question", Toast.LENGTH_SHORT).show();
        }
        else if(answer2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this, "Please answer  the second question", Toast.LENGTH_SHORT).show();
        }
        else{

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2 );
            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "You have answered Security questions successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, SettingsActivity.class);
                        startActivity(intent);


                    }

                }
            });



        }

    }
    private void displayPreviousAnswers(){
        DatabaseReference ref= FirebaseDatabase.getInstance()
                .getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
             String ans1=dataSnapshot.child("answer1").getValue().toString();
                    String ans2=dataSnapshot.child("answer2").getValue().toString();
                    questionOne.setText(ans1);
                    questionTwo.setText(ans2);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}