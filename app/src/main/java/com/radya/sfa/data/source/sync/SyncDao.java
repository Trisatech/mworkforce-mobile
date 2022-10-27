package com.radya.sfa.data.source.sync;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by RadyaLabs PC on 15/12/2017.
 */
@Dao
public interface SyncDao {

    @Query("select * from Sync")
    List<Sync> loadSync();

    @Query("select * from Sync where id = :id and type = :type")
    Sync loadSynced(String id, String type);

    @Insert(onConflict = IGNORE)
    void insertSync(Sync sync);

    @Query("delete from Sync where id = :id")
    void deleteSync(int id);

}
