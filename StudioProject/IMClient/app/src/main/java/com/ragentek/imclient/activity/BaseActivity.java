package com.ragentek.imclient.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.ragentek.imclient.MyApplication;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 15:37
 */
public class BaseActivity extends AppCompatActivity {

    public MyApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        application = ( MyApplication ) getApplication();
    }
}
