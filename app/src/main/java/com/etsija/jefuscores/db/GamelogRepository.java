package com.etsija.jefuscores.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GamelogRepository {
    private GamelogDao gamelogDao;
    private LiveData<List<Gamelog>> allGamelogs;

    public GamelogRepository(Application application) {
        GamelogDatabase database = GamelogDatabase.getInstance(application);
        gamelogDao = database.gamelogDao();
        allGamelogs = gamelogDao.getAllGamelogs();
    }

    // CRUD operations on the database. This is the API that the database
    // exposes to the application.

    public void insert(Gamelog gamelog) {
        new InsertGamelogAsyncTask(gamelogDao).execute(gamelog);
    }

    public void update(Gamelog gamelog) {
        new UpdateGamelogAsyncTask(gamelogDao).execute(gamelog);
    }

    public void delete(Gamelog gamelog) {
        new DeleteGamelogAsyncTask(gamelogDao).execute(gamelog);
    }

    public void deleteAllGamelogs() {
        new DeleteAllGamelogsAsyncTask(gamelogDao).execute();
    }

    public LiveData<List<Gamelog>> getAllGamelogs() {
        return allGamelogs;
    }

    // AsyncTasks to do the database operations on background
    // (because Room doesn't allow database operations on main UI thread).

    private static class InsertGamelogAsyncTask extends AsyncTask<Gamelog, Void, Void> {
        private GamelogDao gamelogDao;

        private InsertGamelogAsyncTask(GamelogDao gamelogDao) {
            this.gamelogDao = gamelogDao;
        }

        @Override
        protected Void doInBackground(Gamelog... gamelogs) {
            gamelogDao.insert(gamelogs[0]);
            return null;
        }
    }

    private static class UpdateGamelogAsyncTask extends AsyncTask<Gamelog, Void, Void> {
        private GamelogDao gamelogDao;

        private UpdateGamelogAsyncTask(GamelogDao gamelogDao) {
            this.gamelogDao = gamelogDao;
        }

        @Override
        protected Void doInBackground(Gamelog... gamelogs) {
            gamelogDao.upoate(gamelogs[0]);
            return null;
        }
    }

    private static class DeleteGamelogAsyncTask extends AsyncTask<Gamelog, Void, Void> {
        private GamelogDao gamelogDao;

        private DeleteGamelogAsyncTask(GamelogDao gamelogDao) {
            this.gamelogDao = gamelogDao;
        }

        @Override
        protected Void doInBackground(Gamelog... gamelogs) {
            gamelogDao.delete(gamelogs[0]);
            return null;
        }
    }

    private static class DeleteAllGamelogsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GamelogDao gamelogDao;

        private DeleteAllGamelogsAsyncTask(GamelogDao gamelogDao) {
            this.gamelogDao = gamelogDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gamelogDao.deleteAllGamelogs();
            return null;
        }
    }
}
