package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Admin.AdminMaintainProductsActivity;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.example.ecommerce.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String type = "";




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }


        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        Paper.init(this);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        final FloatingActionButton fab = findViewById(R.id.fab);
        toolbar.setTitle("Home");
        recyclerView = findViewById(R.id.recycler_menu );
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView= navigationView.getHeaderView(0);
        TextView  userNameTextView= headerView.findViewById(R.id.user_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.profilePic);


        if (!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }




       // userNameTextView.setText(Prevalent.currentOnlineUser.getName());

        //Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
       // Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
       recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);




       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!type.equals("Admin"))
               {
                   Intent intent = new Intent(AdminActivity.this, CartActivity.class);
                   startActivity(intent);
               }
           }
       });


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (type.equals("Admin"))
                                {
                                    Intent intent = new Intent(AdminActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(AdminActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    ////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

      if (id == R.id.action_settings)
        {
            Toast.makeText(AdminActivity.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
           return true;
        }

        return super.onOptionsItemSelected(item);
    }



    ////////////////


/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(AdminActivity.this, "Home is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gallery:
                Toast.makeText(AdminActivity.this, "Gallery is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.slideshow:
                Toast.makeText(AdminActivity.this, "Slide Show is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(AdminActivity.this, "Setting  is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(AdminActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                Paper.book().destroy();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return false;
    }
*/
@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(@NonNull MenuItem item)
{
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.home)
    {

        if (!type.equals("Admin"))
        {
            Intent intent = new Intent(AdminActivity.this, CartActivity.class);
            startActivity(intent);
        }


    }
    else if (id == R.id.search)
    {
        if (!type.equals("Admin"))
        {
            Intent intent = new Intent(AdminActivity.this, SearchProductsActivity.class);
            startActivity(intent);
        }

    }
    else if (id == R.id.slideshow)
    {

        if (!type.equals("Admin"))
        {
            Toast.makeText(AdminActivity.this, "Slideshpw is Selected", Toast.LENGTH_SHORT).show();
        }

    }
    else if (id == R.id.setting)
    {
        if (!type.equals("Admin"))
        {
            Toast.makeText(AdminActivity.this, "Settinge is Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

    }
    else if (id == R.id.logout)
    {

        if (!type.equals("Admin"))
        {
            Paper.book().destroy();

            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}


}