package com.etsija.jefuscores.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Room entity that represents the gamelog table
@Entity(tableName = "gamelog_table")
public class Gamelog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String time;
    private String hometeam;
    private String awayteam;
    private int homescore;
    private int awayscore;
    private String eventlog;

    public Gamelog(String date, String time, String hometeam, String awayteam, int homescore, int awayscore, String eventlog) {
        this.date = date;
        this.time = time;
        this.hometeam = hometeam;
        this.awayteam = awayteam;
        this.homescore = homescore;
        this.awayscore = awayscore;
        this.eventlog = eventlog;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getHometeam() {
        return hometeam;
    }

    public String getAwayteam() {
        return awayteam;
    }

    public int getHomescore() {
        return homescore;
    }

    public int getAwayscore() {
        return awayscore;
    }

    public String getEventlog() {
        return eventlog;
    }
}
