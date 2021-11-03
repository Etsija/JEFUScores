package com.etsija.jefuscores.ui.main;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etsija.jefuscores.JEFUScores;
import com.etsija.jefuscores.R;
import com.etsija.jefuscores.db.Gamelog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GamelogAdapter extends RecyclerView.Adapter<GamelogAdapter.GamelogHolder> {
    JEFUScores app;
    private static final String TAG = "GamelogAdapter";
    private List<Gamelog> gamelogs = new ArrayList<>();
    private List<Gamelog> emailGamelogs = new ArrayList<>();

    @NonNull
    @Override
    public GamelogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gamelog_item, parent, false);
        return new GamelogHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GamelogHolder holder, int position) {
        Gamelog currentGamelog = gamelogs.get(position);

        app = (JEFUScores) holder.itemView.getContext().getApplicationContext();
        holder.tvDate.setText(currentGamelog.getDate());
        holder.tvTime.setText(currentGamelog.getTime());
        holder.tvHometeam.setText(currentGamelog.getHometeam());
        holder.tvAwayteam.setText(currentGamelog.getAwayteam());
        holder.tvHomescore.setText(String.valueOf(currentGamelog.getHomescore()));
        holder.tvAwayscore.setText(String.valueOf(currentGamelog.getAwayscore()));
        holder.tvEvents.setText(currentGamelog.getEventlog());

        // Add/remove gamelogs to email them
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            boolean isSelected = FALSE;

            @Override
            public void onClick(View view) {
                if (isSelected == FALSE) {
                    view.setBackgroundColor(Color.parseColor("#567845"));
                    emailGamelogs.add(currentGamelog);
                    isSelected = TRUE;
                } else {
                    view.setBackgroundColor(Color.parseColor("#3f3f3f"));
                    emailGamelogs.remove(currentGamelog);
                    isSelected = FALSE;
                }
                String body = createGamelog(emailGamelogs);
                body = body + "---";
                body = body + "\nJEFUScores for Android";
                body = body + "\n© Jyrki Keisala, 2021";
                Log.d(TAG, body);
                app.setEmailLogs(body);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamelogs.size();
    }

    public void setGamelogs(List<Gamelog> gamelogs) {
        this.gamelogs = gamelogs;
        notifyDataSetChanged();
    }

    // Get the gamelog at specific position
    public Gamelog getGamelogAt(int position) {
        return gamelogs.get(position);
    }

    class GamelogHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTime;
        private TextView tvHometeam;
        private TextView tvAwayteam;
        private TextView tvHomescore;
        private TextView tvAwayscore;
        private TextView tvEvents;

        public GamelogHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.gamelog_date);
            tvTime = itemView.findViewById(R.id.gamelog_time);
            tvHometeam = itemView.findViewById(R.id.gamelog_hometeam);
            tvAwayteam = itemView.findViewById(R.id.gamelog_awayteam);
            tvHomescore = itemView.findViewById(R.id.gamelog_homescore);
            tvAwayscore = itemView.findViewById(R.id.gamelog_awayscore);
            tvEvents = itemView.findViewById(R.id.gamelog_events);
        }
    }

    public String createGamelog(List<Gamelog> gamelogs) {
        String retStr = "";
        for (Gamelog gamelog : gamelogs) {
            retStr += gamelog.getDate() + " " + gamelog.getTime() + "\n" +
                      gamelog.getHometeam() + " " + gamelog.getHomescore() + " - " +
                      gamelog.getAwayscore() + " " + gamelog.getAwayteam() + "\n" +
                      gamelog.getEventlog() + "\n\n";
        }
        return retStr;
    }
}
