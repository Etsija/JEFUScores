package com.etsija.jefuscores;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class JEFUScores extends Application {
    private Team hometeam, awayteam;
    private int goal = 6;
    private int goalAdd = 2;
    private String email = "name@gmail.com";
    private String password = "fiifoo";
    private String email1 = "";
    private String email2 = "";
    private String email3 = "";
    private String email4 = "";
    private String email5 = "";
    private int homeScore = 0;
    private int awayScore = 0;
    List<String> gameLog = new ArrayList<String>();
    private String emailLogs = "JEFUScores for Android";

    // Getters and setters for the application global variables

    public Team getHometeam() { return hometeam; }

    public void setHometeam(Team hometeam) { this.hometeam = hometeam; }

    public Team getAwayteam() { return awayteam; }

    public void setAwayteam(Team awayteam) { this.awayteam = awayteam; }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getGoalAdd() {
        return goalAdd;
    }

    public void setGoalAdd(int goalAdd) {
        this.goalAdd = goalAdd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail1() { return email1; }

    public void setEmail1(String email1) { this.email1 = email1; }

    public String getEmail2() { return email2; }

    public void setEmail2(String email2) { this.email2 = email2; }

    public String getEmail3() { return email3; }

    public void setEmail3(String email3) { this.email3 = email3; }

    public String getEmail4() { return email4; }

    public void setEmail4(String email4) { this.email4 = email4; }

    public String getEmail5() { return email5; }

    public void setEmail5(String email5) { this.email5 = email5; }

    public int getHomeScore() { return homeScore; }

    public void setHomeScore(int homeScore) { this.homeScore = homeScore; }

    public int getAwayScore() { return awayScore; }

    public void setAwayScore(int awayScore) { this.awayScore = awayScore; }

    public List<String> getGameLog() { return gameLog; }

    public void setGameLog(List<String> gameLog) { this.gameLog = gameLog; }

    public String getEmailLogs() {
        return emailLogs;
    }

    public void setEmailLogs(String emailLogs) {
        this.emailLogs = emailLogs;
    }

    // Helper methods

    // Calculate team score
    public int calcScore(Team team) {
        return team.getGoals() * getGoal() + team.getGoalsAdd() * getGoalAdd();
    }

}
