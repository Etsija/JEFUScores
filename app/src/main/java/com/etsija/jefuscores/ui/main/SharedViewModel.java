package com.etsija.jefuscores.ui.main;

import com.etsija.jefuscores.Team;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Team> hometeam = new MutableLiveData<>();
    private MutableLiveData<Team> awayteam = new MutableLiveData<>();
    private MutableLiveData<Integer> goal = new MutableLiveData<>();
    private MutableLiveData<Integer> goalAdd = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> email1 = new MutableLiveData<>();
    private MutableLiveData<String> email2 = new MutableLiveData<>();
    private MutableLiveData<String> email3 = new MutableLiveData<>();
    private MutableLiveData<String> email4 = new MutableLiveData<>();
    private MutableLiveData<String> email5 = new MutableLiveData<>();
    private MutableLiveData<Integer> homescore = new MutableLiveData<>();
    private MutableLiveData<Integer> awayscore = new MutableLiveData<>();
    private MutableLiveData<List<String>> gamelog = new MutableLiveData<>();

}
