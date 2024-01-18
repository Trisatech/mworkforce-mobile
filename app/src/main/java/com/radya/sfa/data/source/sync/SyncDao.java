package com.radya.sfa.data.source.sync;


import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
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
