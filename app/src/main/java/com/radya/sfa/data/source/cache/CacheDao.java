package com.radya.sfa.data.source.cache;


import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by RadyaLabs PC on 15/12/2017.
 */
@Dao
public interface CacheDao {

    @Query("select * from Cache where id = :id")
    Cache loadCacheById(int id);

    @Insert(onConflict = IGNORE)
    void insertCache(Cache cache);

    @Update(onConflict = REPLACE)
    void updateCache(Cache cache);

}
