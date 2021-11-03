package com.etsija.jefuscores;

public class Team {

    private Type type;
    private String name;
    private int goals;
    private int goalsAdd;

    // Constructors

    public Team() {
        this.type = Type.HOME;
        this.name = "Joukkueen nimi";
        this.goals = 0;
        this.goalsAdd = 0;
    }

    public Team(Type type, String name) {
        this.type = type;
        this.name = name;
        this.goals = 0;
        this.goalsAdd = 0;
    }

    public Team(Type type, String name, int goals, int goalsAdd) {
        this.type = type;
        this.name = name;
        this.goals = goals;
        this.goalsAdd = goalsAdd;
    }

    // Getters and setters

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getGoals() { return goals; }

    public void setGoals(int goals) { this.goals = goals; }

    public int getGoalsAdd() { return goalsAdd; }

    public void setGoalsAdd(int goalsAdd) { this.goalsAdd = goalsAdd; }

    // Increase/decrease goals and additional goals

    public void addGoal() { this.goals++; }

    public void removeGoal() {
        if (this.goals > 0) this.goals--;
    }

    public void addGoalAdd() {
        this.goalsAdd++;
    }

    public void removeGoalAdd() {
        if (this.goalsAdd > 0) this.goalsAdd--;
    }

    // Print team

    @Override
    public String toString() {
        return "Team{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", goals=" + goals +
                ", goalsAdd=" + goalsAdd +
                '}';
    }
}
