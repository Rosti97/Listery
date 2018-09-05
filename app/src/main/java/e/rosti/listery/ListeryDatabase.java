package e.rosti.listery;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Roommates.class, Item.class}, version = 1, exportSchema = false)
public abstract class ListeryDatabase extends RoomDatabase{
    public abstract DaoAccess mdaoAccess();

    private static ListeryDatabase INSTANCE;


    static ListeryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ListeryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ListeryDatabase.class, "word_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
