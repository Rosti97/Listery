package e.rosti.listery;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.concurrent.Executors;

@Database(entities = {Mate.class, Item.class}, version = 1, exportSchema = false)
public abstract class ListeryDatabase extends RoomDatabase {
    private static ListeryDatabase INSTANCE;

    static ListeryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ListeryDatabase.class) {
                if (INSTANCE == null) {
                    Callback rdc = new Callback() {
                        public void onCreate(SupportSQLiteDatabase db) {
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getDatabase(context).mdaoAccess().insertMates(new Mate("Ich", 0));
                                }
                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ListeryDatabase.class, "word_database").addCallback(rdc)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public abstract DaoAccess mdaoAccess();
}
