package com.etsija.jefuscores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.etsija.jefuscores.db.Gamelog;
import com.etsija.jefuscores.ui.main.GamelogViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class FragmentGame extends Fragment {

    JEFUScores app;
    private static final String TAG = "FragmentGame";
    private TextView tvHometeam, tvAwayteam, tvGametime, tvHomeScore, tvAwayScore;
    private TextClock tcTime;
    private String startTime;
    private Button btnStartGame, btnStopGame;
    private Button btnHomeDecGoal, btnHomeIncGoal, btnHomeAddGoal;
    private Button btnAwayDecGoal, btnAwayIncGoal, btnAwayAddGoal;
    private TableLayout tlHome, tlAway;
    private boolean isHomeAddGoal = FALSE;
    private boolean isAwayAddGoal = FALSE;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private boolean reset;
    List<String> gamelog = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        // Get access to global variables
        app = (JEFUScores) getActivity().getApplication();

        if (savedInstanceState != null) {
            // Get the previous state of the stopwatch if the fragment
            // has been destroyed or recreated
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        // Create the timer
        runTimer();

        // Find the needed elements from the layout
        tcTime = view.findViewById(R.id.tcTime);
        tvHometeam = view.findViewById(R.id.gamelog_hometeam);
        tvAwayteam = view.findViewById(R.id.tvAwayteam);
        tvGametime = view.findViewById(R.id.tvGameTime);
        tvHomeScore = view.findViewById(R.id.tvHomeScore);
        tvAwayScore = view.findViewById(R.id.tvAwayScore);
        btnStartGame = view.findViewById(R.id.btnStartGame);
        btnStopGame = view.findViewById(R.id.btnStopGame);
        btnHomeDecGoal = view.findViewById(R.id.btnHomeDecGoal);
        btnHomeIncGoal = view.findViewById(R.id.btnHomeIncGoal);
        btnHomeAddGoal = view.findViewById(R.id.btnHomeAddGoal);
        btnAwayDecGoal = view.findViewById(R.id.btnAwayDecGoal);
        btnAwayIncGoal = view.findViewById(R.id.btnAwayIncGoal);
        btnAwayAddGoal = view.findViewById(R.id.btnAwayAddGoal);
        tlHome = view.findViewById(R.id.tlHome);
        tlAway = view.findViewById(R.id.tlAway);

        // Reset and initialise score tables
        resetTable(tlHome);
        resetTable(tlAway);
        initTable(tlHome, "K: " + app.getHometeam().getName());
        initTable(tlAway, "V: " + app.getAwayteam().getName());

        // Initialise button states

        // Start/stop game
        enableBtn(btnStartGame);
        disableBtn(btnStopGame);

        // Hometeam
        disableBtn(btnHomeDecGoal);
        disableBtn(btnHomeIncGoal);
        disableBtn(btnHomeAddGoal);

        // Awayteam
        disableBtn(btnAwayDecGoal);
        disableBtn(btnAwayIncGoal);
        disableBtn(btnAwayAddGoal);

        // Button listeners

        // Start/stop game

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableBtn(btnStartGame);
                enableBtn(btnStopGame);
                enableBtn(btnHomeIncGoal);
                enableBtn(btnAwayIncGoal);
                resetTable(tlHome);
                resetTable(tlAway);
                initTable(tlHome, "K: " + app.getHometeam().getName());
                initTable(tlAway, "V: " + app.getAwayteam().getName());
                startTime = tcTime.getText().toString();
                addLog(startTime, "Peli alkoi");

                // Start the game timer
                running = true;
                reset = false;
            }
        });

        btnStopGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ask the user before deleting a gamelog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Lopeta")
                        .setMessage("Lopetetaanko peli?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("KYLLÄ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Yes button clicked -> save the game log and reset view
                                Toast.makeText(getContext(), "Peli päättyi", Toast.LENGTH_SHORT).show();
                                resetTable(tlHome);
                                resetTable(tlAway);
                                initTable(tlHome, "K: " + app.getHometeam().getName());
                                initTable(tlAway, "V: " + app.getAwayteam().getName());
                                enableBtn(btnStartGame);
                                disableBtn(btnStopGame);
                                disableBtn(btnHomeDecGoal);
                                disableBtn(btnHomeIncGoal);
                                disableBtn(btnHomeAddGoal);
                                disableBtn(btnAwayDecGoal);
                                disableBtn(btnAwayIncGoal);
                                disableBtn(btnAwayAddGoal);
                                addLog(tcTime.getText().toString(), "Peli päättyi");

                                // Stop the game timer
                                running = false;
                                reset = true;

                                // Save gamelog to database and reset the global variable
                                // used for the gamelog
                                saveGamelog();
                                resetScoreAndLog();
                            }
                        })
                        .setNegativeButton("EI", null)
                        .show();
            }
        });

        // Hometeam

        btnHomeDecGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getHometeam().removeGoal();
                removeLog();
                if (isHomeAddGoal == TRUE) {
                    app.getHometeam().removeGoalAdd();
                    removeLog();
                    isHomeAddGoal = FALSE;
                }
                updateHomeScore();
                if (app.getHometeam().getGoals() == 0) disableBtn(btnHomeDecGoal);
                updateTable(tlHome, tvGametime.getText().toString(), "decGoal");
            }
        });

        btnHomeIncGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getHometeam().addGoal();
                updateHomeScore();
                disableBtn(btnAwayAddGoal);
                if (app.getHometeam().getGoals() > 0) enableBtn(btnHomeDecGoal);
                isHomeAddGoal = FALSE;
                enableBtn(btnHomeAddGoal);
                addLog(tvGametime.getText().toString(),
                       String.format("%3d", app.getHomeScore()) + "-" +
                       String.format("%-3d", app.getAwayScore()) + "  " +
                       app.getHometeam().getName() + " : " +
                       "MAALI");
                updateTable(tlHome, tvGametime.getText().toString(), "incGoal");
            }
        });

        btnHomeAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getHometeam().addGoalAdd();
                updateHomeScore();
                isHomeAddGoal = TRUE;
                disableBtn(btnHomeAddGoal);
                addLog(tvGametime.getText().toString(),
                       String.format("%3d", app.getHomeScore()) + "-" +
                       String.format("%-3d", app.getAwayScore()) + "  " +
                       app.getHometeam().getName() + " : " +
                       "LISÄMAALI");
                updateTable(tlHome, tvGametime.getText().toString(), "incGoalAdd");
            }
        });

        // Awayteam

        btnAwayDecGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getAwayteam().removeGoal();
                removeLog();
                if (isAwayAddGoal == TRUE) {
                    app.getAwayteam().removeGoalAdd();
                    removeLog();
                    isAwayAddGoal = FALSE;
                }
                updateAwayScore();
                if (app.getAwayteam().getGoals() == 0) disableBtn(btnAwayDecGoal);
                updateTable(tlAway, tvGametime.getText().toString(), "decGoal");
            }
        });

        btnAwayIncGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getAwayteam().addGoal();
                updateAwayScore();
                disableBtn(btnHomeAddGoal);
                if (app.getAwayteam().getGoals() > 0) enableBtn(btnAwayDecGoal);
                isAwayAddGoal = FALSE;
                enableBtn(btnAwayAddGoal);
                addLog(tvGametime.getText().toString(),
                       String.format("%3d", app.getHomeScore()) + "-" +
                       String.format("%-3d", app.getAwayScore()) + "  " +
                       app.getAwayteam().getName() + " : " +
                       "MAALI");
                updateTable(tlAway, tvGametime.getText().toString(), "incGoal");
            }
        });

        btnAwayAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getAwayteam().addGoalAdd();
                updateAwayScore();
                isAwayAddGoal = TRUE;
                disableBtn(btnAwayAddGoal);
                addLog(tvGametime.getText().toString(),
                       String.format("%3d", app.getHomeScore()) + "-" +
                       String.format("%-3d", app.getAwayScore()) + "  " +
                       app.getAwayteam().getName() + " : " +
                       "LISÄMAALI");
                updateTable(tlAway, tvGametime.getText().toString(), "incGoalAdd");
            }
        });
    }

    // When the fragment becomes visible, initialise Hometeam and Awayteam
    // from the global variables
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d(TAG, "is visible");
            tvHometeam.setText(app.getHometeam().getName());
            tvAwayteam.setText(app.getAwayteam().getName());
        } else {
            Log.d(TAG, "not longer visible");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) running = true;
    }

    // Helper methods

    void enableBtn(Button btn) {
        int enableColor = getResources().getColor(R.color.tanAccent);
        btn.setEnabled(true);
        btn.setTextColor(enableColor);
        btn.getBackground().setAlpha(255);
    }

    void disableBtn(Button btn) {
        int disableColor = getResources().getColor(R.color.greyEnd);
        btn.setEnabled(false);
        btn.setTextColor(disableColor);
        btn.getBackground().setAlpha(32);
    }

    void updateHomeScore() {
        int score = app.calcScore(app.getHometeam());
        app.setHomeScore(score);
        tvHomeScore.setText(Integer.toString(score));
    }

    void updateAwayScore() {
        int score = app.calcScore(app.getAwayteam());
        app.setAwayScore(score);
        tvAwayScore.setText(Integer.toString(score));
    }

    // Reset the game log
    void resetScoreAndLog() {
        //app.getGameLog().clear();
        gamelog.clear();
        app.getHometeam().setGoals(0);
        app.getHometeam().setGoalsAdd(0);
        app.getAwayteam().setGoals(0);
        app.getAwayteam().setGoalsAdd(0);
        updateHomeScore();
        updateAwayScore();
    }

    // Append new game event to the game log
    void addLog(String timestamp, String event) {
        String str = timestamp + "  " + event;
        gamelog.add(str);
    }

    // Remove the last (newest) event from the game log (in case of mistype of goal)
    void removeLog() {
        gamelog.remove(gamelog.size() - 1);
    }

    // Convert the game log to multiline string
    String convertGamelog() {
        String body = "";
        for (String event : gamelog) {
            body = body + event + "\n";
        }
        return body;
    }

    void saveGamelog() {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String date = df.format(d);
        String time = startTime;
        String hometeam = app.getHometeam().getName();
        String awayteam = app.getAwayteam().getName();
        int homescore = app.getHomeScore();
        int awayscore = app.getAwayScore();
        String eventlog = convertGamelog();

        // Save the gamelog entity to database
        // This actually should NOT be here!
        Gamelog gamelog = new Gamelog(date, time,
                hometeam, awayteam,
                homescore, awayscore,
                eventlog);

        // getActivity() -> requireActivity()
        GamelogViewModel gamelogViewModel = new ViewModelProvider(requireActivity())
                .get(GamelogViewModel.class);
        gamelogViewModel.insert(gamelog);
        Toast.makeText(getContext(), "Peli tallennettu", Toast.LENGTH_SHORT).show();
    }

    // The runTimer() method implements the game clock
    private void runTimer()
    {
        // Creates a new Handler
        final Handler handler = new Handler();

        // Call the post() method, passing in a new Runnable.
        // The post() method processes code without a delay,
        // so the code in the Runnable will run almost immediately
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes, and seconds
                String time = String.format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text
                tvGametime.setText(time);

                // If running is true, increment the seconds variable
                if (running) {
                    seconds++;
                }

                // If reset is true, reset the clock
                if (reset) {
                    seconds = 0;
                }

                // Post the code again with a delay of 1 second
                handler.postDelayed(this, 1000);
            }
        });
    }

    // Clear the scoretable
    public void resetTable(TableLayout tl) {
        //int count = tl.getChildCount();
        //for (int i = 0; i < count; i++) {
        //    View child = tl.getChildAt(i);
        //    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        //}
        tl.removeAllViews();
    }

    // Initialise the scoretable
    public void initTable(TableLayout tl, String strTeam) {

        TableRow row0 = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());

        TableLayout.LayoutParams lp =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 5, 20, 5);
        row0.setLayoutParams(lp);
        tv0.setText(strTeam);
        tv0.setTextColor(Color.parseColor("#F44336"));
        row0.addView(tv0);
        tl.addView(row0, lp);
    }

    // Update the scoretable
    public void updateTable(TableLayout tl, String timestamp, String type) {

        int rows = tl.getChildCount();
        TableRow row = new TableRow(getActivity());

        switch (type) {
            case "incGoal":
                TextView tv1 = new TextView(getActivity());
                TextView tv2 = new TextView(getActivity());
                tv1.setText("   " + timestamp);
                tv1.setTextColor(getResources().getColor(R.color.tanAccent));
                tv1.setTextSize(10);
                row.addView(tv1);
                tv2.setText("M  ");
                tv2.setTextColor(getResources().getColor(R.color.greenAccent));
                tv2.setTextSize(10);
                tv2.setTypeface(null, Typeface.BOLD);
                row.addView(tv2);
                tl.addView(row);
                break;
            case "incGoalAdd":
                row = (TableRow) tl.getChildAt(rows-1);
                //TextView cell = (TextView) row.getChildAt(1);
                TextView tv = new TextView(getActivity());
                tv.setText("L");
                tv.setTextColor(Color.YELLOW);
                tv.setTextSize(10);
                tv.setTypeface(null, Typeface.BOLD);
                row.addView(tv);
                break;
            case "decGoal":
                // Remove last line in scoretable
                tl.removeViewAt(rows-1);
                break;
            default:
                break;
        }
    }

}
