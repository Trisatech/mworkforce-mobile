package com.radya.sfa.util;

import androidx.work.WorkManager;

public class WorkManagerUtils {

    public static WorkManager getWorkManager(){
        return WorkManager.getInstance();
    }

}
