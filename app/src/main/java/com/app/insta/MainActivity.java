package com.app.insta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this,R.id.main_navhost_frag);
        NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(nav,navController);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            return navController.navigateUp();
        }
        return super.onOptionsItemSelected(item);
    }
}
