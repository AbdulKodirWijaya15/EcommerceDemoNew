package manish.ecommerce.com.ecommercedemo.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Fragments.HomeFragment;
import manish.ecommerce.com.ecommercedemo.R;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        int backPressed;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.appname);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        textView.setTypeface(typeface);
  //      setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        currentPageLoader();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.closeDrawers();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            getFragmentManager().beginTransaction().replace(R.id.dashFrame, new HomeFragment()).commit();
        } else if(id== R.id.my_orders){

        }else if(id== R.id.wishlist){
            startActivity(new Intent(DashBoard.this, WishListActivity.class));
        }else if(id== R.id.cart){
            startActivity(new Intent(DashBoard.this, MyCart.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void currentPageLoader() {
        if (Constants.myFlow.equals("dash_home_fragment")) {
            getFragmentManager().beginTransaction().replace(R.id.dashFrame, new HomeFragment()).addToBackStack(null).commit();
        } else if(Constants.myFlow.equals("Wish_list")){
          startActivity(new Intent(DashBoard.this, WishListActivity.class));
        } else {
            getFragmentManager().beginTransaction().replace(R.id.dashFrame, new HomeFragment()).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(!Constants.myFlow.equals("dash_home_fragment")){
            getFragmentManager().beginTransaction().replace(R.id.dashFrame, new HomeFragment()).commit();
        } else if (backPressed == 2) {
            backPressed--;
            Toast.makeText(DashBoard.this, "Press Back Once Again to close the App", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run () {
                    if(backPressed==1){
                        backPressed++;
                    }
                }
            },2000);
        } else if (backPressed == 1) {
            super.onBackPressed();
            finish();
        }
        super.onBackPressed();
    }
}
