package com.etsija.jefuscores.db;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Gamelog.class}, version = 1)
public abstract class GamelogDatabase extends RoomDatabase {
    private static GamelogDatabase instance;
    public abstract GamelogDao gamelogDao();

    public static synchronized GamelogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GamelogDatabase.class, "gamelog_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // Populate the db with some default logs when created
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GamelogDao gamelogDao;

        private PopulateDbAsyncTask(GamelogDatabase db) {
            gamelogDao = db.gamelogDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gamelogDao.insert(new Gamelog("2021-11-01", "18:45",
                    "Falcons", "Devils", 16, 6,
                    "18:45  Peli alkoi\n" +
                            "0:00:06    6-0    Falcons : MAALI\n" +
                            "0:00:12    8-0    Falcons : LISÄMAALI\n" +
                            "0:00:19    8-6    Bulldogs : MAALI\n" +
                            "0:00:27   14-6    Falcons : MAALI\n" +
                            "0:00:32   16-6    Falcons : LISÄMAALI\n" +
                            "18:45  Peli päättyi"));
            gamelogDao.insert(new Gamelog("2021-10-23", "12:04",
                    "Crocodiles", "Falcons", 8, 6,
                    "12:04  Peli alkoi\n" +
                            "0:10:06    6-0    Crocodiles : MAALI\n" +
                            "0:11:12    8-0    Crocodiles : LISÄMAALI\n" +
                            "0:54:19    8-6    Falcons : MAALI\n" +
                            "13:16  Peli päättyi"));
            return null;
        }
    }

}
