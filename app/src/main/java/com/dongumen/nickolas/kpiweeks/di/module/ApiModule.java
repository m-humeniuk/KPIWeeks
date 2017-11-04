package com.dongumen.nickolas.kpiweeks.di.module;


import com.dongumen.nickolas.kpiweeks.Constants;
import com.dongumen.nickolas.kpiweeks.api.KpiApi;
import com.dongumen.nickolas.kpiweeks.model.remote.ISearchDataSource;
import com.dongumen.nickolas.kpiweeks.model.remote.IWeekDataSource;
import com.dongumen.nickolas.kpiweeks.model.remote.SearchDataSource;
import com.dongumen.nickolas.kpiweeks.model.remote.WeekDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.INSTANCE.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

   @Provides
   @Singleton
   ISearchDataSource provideSearchDataSource(Retrofit retrofit) {
       return new SearchDataSource(retrofit.create(KpiApi.class));
   }

    @Provides
    @Singleton
    IWeekDataSource provideWeekDataSource(Retrofit retrofit) {
        return new WeekDataSource(retrofit.create(KpiApi.class));
    }

}
