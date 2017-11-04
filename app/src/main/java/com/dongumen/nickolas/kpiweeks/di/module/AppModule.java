package com.dongumen.nickolas.kpiweeks.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences providePreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return Executors.newCachedThreadPool();
    }
/*
    @Provides
    @Singleton
    public SharedPreferenceUtils provideSharedPreferencesUtils(Context context) {
        return new SharedPreferenceUtils(context);
    }

    @Provides
    @Singleton
    public DayInformationUtil provideDayInformationUtil() {
        return new DayInformationUtil();
    }

    @Provides
    @Singleton
    public ResponseToScheduleUtil provideResponseToScheduleUtil() {
        return new ResponseToScheduleUtil();
    }*/
}
