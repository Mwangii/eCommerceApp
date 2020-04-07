package com.example.ecommerce.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ecommerce.Buyers.AdminActivity;
import com.example.ecommerce.Buyers.LoginActivity;
import com.example.ecommerce.R;

public class AdminManageActivity  extends AppCompatActivity {

    private ImageView tShirts,sports,dresses,sweaters;
    private ImageView  glasses,hats,purses,shoes;
    private  ImageView  headPhones,laptops,watches,phones;
    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn,approveProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);


        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        maintainProductsBtn = (Button) findViewById(R.id.maintain_btn);
        approveProducts = (Button) findViewById(R.id.check_approve);



        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminManageActivity.this, AdminActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminManageActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminManageActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });
        approveProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminManageActivity.this, CheckNewProductsActivity.class);
                startActivity(intent);
            }
        });



        tShirts=(ImageView) findViewById(R.id.tshits);
        sports=(ImageView) findViewById(R.id.sports);
        dresses=(ImageView) findViewById(R.id.dress);
        sweaters=(ImageView) findViewById(R.id.sweater);
        glasses=(ImageView) findViewById(R.id.glasses);
        hats=(ImageView) findViewById(R.id.hats);
        purses=(ImageView) findViewById(R.id.purses);
        shoes=(ImageView) findViewById(R.id.shoes);
        headPhones=(ImageView) findViewById(R.id.headphones);
        laptops=(ImageView) findViewById(R.id.laptops);
        watches=(ImageView) findViewById(R.id.watches);
        phones=(ImageView) findViewById(R.id.phones);


/////////////////////////tshirts image
      /*  tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);
            }
        });
        ///sports
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","SportTShirts");
                startActivity(intent);
            }
        });
        // dresses
        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","dresses");
                startActivity(intent);
            }
        });
        //////sweaters
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);
            }
        });

        //glasses
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });
        //hats
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Hats");
                startActivity(intent);
            }
        });
        //purses
        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","purses");
                startActivity(intent);
            }
        });
        //shoes
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        //
        //headPhones
        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Head Phones");
                startActivity(intent);
            }
        });
        ///laptops
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        //watches
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        //phones
        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","phones");
                startActivity(intent);
            }
        });
        //////////////////////////////////////////////*/

    }
}
