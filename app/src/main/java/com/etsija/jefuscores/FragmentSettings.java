package com.etsija.jefuscores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSettings extends Fragment {

    JEFUScores app;
    private static final String TAG = "SettingsFragment";
    private EditText etHometeam, etAwayteam, etGoal, etGoalAdd, etEmail, etPassword;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the EditText elements from the layout
        etHometeam = view.findViewById(R.id.etHometeam);
        etAwayteam = view.findViewById(R.id.etAwayteam);
        etGoal = view.findViewById(R.id.etGoal);
        etGoalAdd = view.findViewById(R.id.etGoalAdd);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSave = view.findViewById(R.id.btnSave);

        // Initialise them
        // Get access to global variables
        app = (JEFUScores) getActivity().getApplication();
        etHometeam.setText(app.getHometeam().getName());
        etAwayteam.setText(app.getAwayteam().getName());
        etGoal.setText(Integer.toString(app.getGoal()));
        etGoalAdd.setText(Integer.toString(app.getGoalAdd()));
        etEmail.setText(app.getEmail());
        etPassword.setText(app.getPassword());
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");

        // Listen to changes in the settings EditText fields
        // and save the changed values to global variables
        etHometeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.getHometeam().setName(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etAwayteam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.getAwayteam().setName(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                if (c.length() > 0)
                    app.setGoal(Integer.parseInt(c.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etGoalAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                if (c.length() > 0)
                    app.setGoalAdd(Integer.parseInt(c.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setPassword(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        // Save preferences
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
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
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause()");
    }
}
