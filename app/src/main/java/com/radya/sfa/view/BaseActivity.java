package com.radya.sfa.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.radya.sfa.MyApplication;
import com.radya.sfa.data.AppDatabase;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().setActivity(this);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
