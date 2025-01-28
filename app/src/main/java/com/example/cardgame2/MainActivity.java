package com.example.cardgame2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.cardgame2.database.CardDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private CardDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        dbHelper = new CardDatabaseHelper(this);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);

        // Load initial fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Insert sample card data into SQLite database
        insertSampleData();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
        item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_play) {
                selectedFragment = new PlayFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            } else if (itemId == R.id.nav_rules) {
                selectedFragment = new RulesFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            }
            return true;
        };

    private void loadFragment(Fragment fragment) {
        // Simple fragment replacement without animations
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    // Method to insert sample data into the SQLite database
    private void insertSampleData() {
        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Prepare values for card data
        ContentValues values = new ContentValues();
        values.put(CardDatabaseHelper.COLUMN_NAME, "Pikachu");
        values.put(CardDatabaseHelper.COLUMN_TYPE, "Electric");
        values.put(CardDatabaseHelper.COLUMN_HP, 60);
        values.put(CardDatabaseHelper.COLUMN_ATTACK, 50);
        values.put(CardDatabaseHelper.COLUMN_IMAGE_RES, R.drawable.pikachu);
        values.put(CardDatabaseHelper.COLUMN_BACKGROUND_RES, R.drawable.pokemon_card_bg_yellow);
        values.put(CardDatabaseHelper.COLUMN_RARITY_RES, R.drawable.button_gradient_background);

        // Insert into the database
        long newRowId = db.insert(CardDatabaseHelper.TABLE_CARDS, null, values);

        if (newRowId == -1) {
            // Log error if insertion failed
            System.out.println("Error inserting data");
        } else {
            // Log success message with the row ID
            System.out.println("Card inserted with row ID: " + newRowId);
        }

        // Close the database connection
        db.close();
    }

    @Override
    public void onBackPressed() {
        if (bottomNav.getSelectedItemId() != R.id.nav_home) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        } else {
            super.onBackPressed();
        }
    }
}
