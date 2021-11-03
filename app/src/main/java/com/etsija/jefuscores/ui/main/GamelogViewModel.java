package com.etsija.jefuscores.ui.main;

import android.app.Application;

import com.etsija.jefuscores.db.Gamelog;
import com.etsija.jefuscores.db.GamelogRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GamelogViewModel extends AndroidViewModel {
    private GamelogRepository repository;
    private LiveData<List<Gamelog>> allGamelogs;


    public GamelogViewModel(@NonNull Application application) {
        super(application);
        repository = new GamelogRepository(application);
        allGamelogs = repository.getAllGamelogs();
    }

    public void insert(Gamelog gamelog) {
        repository.insert(gamelog);
    }

    public void update(Gamelog gamelog) {
        repository.update(gamelog);
    }

    public void delete(Gamelog gamelog) {
        repository.delete(gamelog);
    }

    public void deleteAllGamelogs() {
        repository.deleteAllGamelogs();
    }

    public LiveData<List<Gamelog>> getAllGamelogs() {
        return allGamelogs;
    }
}
