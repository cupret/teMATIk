package id.ac.umn.tematik;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Promo.class, Music.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase localDatabase;

    public abstract ProductQuery productQuery();
    public abstract PromoQuery promoQuery();
    public abstract MusicQuery musicQuery();

    public static LocalDatabase getInstance(final Context context){
        if(localDatabase == null){
            synchronized (LocalDatabase.class) {
                if(localDatabase == null){
                    localDatabase = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "LocalDB").build();
                }
            }
        }

        return localDatabase;
    }
}
