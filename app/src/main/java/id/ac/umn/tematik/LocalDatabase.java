package id.ac.umn.tematik;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class, Promo.class, PlayList.class}, version = 1, exportSchema = false)
@TypeConverters({StringListConverter.class, IntegerListConverter.class, SpesifikasiBerlianConverter.class})
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase localDatabase;

    public abstract ProductQuery productQuery();
    public abstract PromoQuery promoQuery();
    public abstract PlayListQuery playListQuery();

    public static LocalDatabase getInstance(final Context context){
        if(localDatabase == null){
            synchronized (LocalDatabase.class) {
                if(localDatabase == null){
                    localDatabase = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "LocalDB.db").build();
                }
            }
        }

        return localDatabase;
    }
}
