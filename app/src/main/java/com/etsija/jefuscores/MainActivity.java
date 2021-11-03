package com.etsija.jefuscores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.etsija.jefuscores.ui.main.SectionsPagerAdapter;
import com.etsija.jefuscores.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    JEFUScores app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // Get access to global variables
        app = (JEFUScores) this.getApplication();
    }

    // Store the data to the SharedPreference in the onPause() method
    // When the user closes the application, onPause() will be called
    // and data will be stored
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Hometeam", app.getHometeam().getName());
        editor.putString("Awayteam", app.getAwayteam().getName());
        editor.putInt("Goal", app.getGoal());
        editor.putInt("GoalAdditional", app.getGoalAdd());
        editor.putString("Email", app.getEmail());
        editor.putString("Password", app.getPassword());
        editor.putString("Email1", app.getEmail1());
        editor.putString("Email2", app.getEmail2());
        editor.putString("Email3", app.getEmail3());
        editor.putString("Email4", app.getEmail4());
        editor.putString("Email5", app.getEmail5());
        editor.apply();
    }

    // Fetch the stored data in onResume() because this is what will be called
    // when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        app.setHometeam(new Team(Type.HOME, sp.getString("Hometeam", "")));
        app.setAwayteam(new Team(Type.AWAY, sp.getString("Awayteam", "")));
        app.setGoal(sp.getInt("Goal", 0));
        app.setGoalAdd(sp.getInt("GoalAdditional", 0));
        app.setEmail(sp.getString("Email", ""));
        app.setPassword(sp.getString("Password", ""));
        app.setEmail1(sp.getString("Email1", ""));
        app.setEmail2(sp.getString("Email2", ""));
        app.setEmail3(sp.getString("Email3", ""));
        app.setEmail4(sp.getString("Email4", ""));
        app.setEmail5(sp.getString("Email5", ""));
    }
}