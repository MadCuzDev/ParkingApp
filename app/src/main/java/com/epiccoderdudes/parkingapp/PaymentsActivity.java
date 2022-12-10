package com.epiccoderdudes.parkingapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class PaymentsActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    private Button payNowButton;

    private EditText dateInput, durationInput, plateInput;

    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        navigationView = findViewById(R.id.navView);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(navigationView);

        payNowButton = findViewById(R.id.payNowButton);

        dateInput = findViewById(R.id.dateInput);
        durationInput = findViewById(R.id.durationInput);
        plateInput = findViewById(R.id.plateInput);

        payNowButton.setOnClickListener(view -> {
            if (dateInput.getText().toString().isEmpty() || dateInput.getText().toString().isEmpty() || dateInput.getText().toString().isEmpty()) {
                // error
            } else {
                databaseHandler.addParking("Kitchener", dateInput.getText().toString(), durationInput.getText().toString(), plateInput.getText().toString());
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                Toast.makeText(getApplicationContext(), "Payment successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Class startClass;
        switch(menuItem.getItemId()) {
            case R.id.gotoHistory:
                startClass = HistoryActivity.class;
                break;
            case R.id.gotoParking:
                startClass = FindParkingActivity.class;
                break;
            case R.id.gotoPayments:
                startClass = PaymentsActivity.class;
                break;
            case R.id.gotoHome:
            default:
                startClass = MainActivity.class;
        }

        startActivity(new Intent(getApplicationContext(), startClass));

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}