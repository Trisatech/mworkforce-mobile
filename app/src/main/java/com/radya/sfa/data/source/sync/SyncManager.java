package com.radya.sfa.data.source.sync;

import com.radya.sfa.data.AppDatabase;

/**
 * Created by RadyaLabs PC on 12/12/2017.
 */

public class SyncManager {

    public static void storeSync(AppDatabase db, String id, String type, String data, int postType) {
        Sync synced = db.syncDao().loadSynced(id, type);
        Sync sync = new Sync();

        if (synced == null){
            sync.assignment_id = id;
            sync.json = data;
            sync.type = type;
            sync.post_type = postType; //0 = json, 1 = multipart
            db.syncDao().insertSync(sync);
        }

    }

    public static void deleteSync(AppDatabase db, int id){
        db.syncDao().deleteSync(id);
    }

}
