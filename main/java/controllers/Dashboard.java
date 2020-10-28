package controllers;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ristodroid.R;

import controllers.ui.menu.MenuFragment;

/**
 * In questa classe ci occupiamo di scambiare i vari fragment
 */
public class Dashboard extends AppCompatActivity  {

    private NavHostFragment navHostFragment;
    private BottomNavigationView navMenu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navHostFragment.getNavController().navigateUp();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu, R.id.navigation_summary, R.id.navigation_receipt)
                .build();

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navMenu= this.findViewById(R.id.nav_view);
        setSummaryBadge(navMenu);
    }

    protected void setSummaryBadge(BottomNavigationView navMenu) {
        boolean orderNotNull = MainActivity.getOrder()!=null && MainActivity.getOrder().getOrderDetails().size()>0;
        if(orderNotNull) {
            navMenu.getOrCreateBadge(R.id.navigation_summary).setNumber(MainActivity.getOrder().getOrderDetails().size());
        }
    }

}