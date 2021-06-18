package com.softaai.unittesting.di.module

import android.app.Application
import com.softaai.unittesting.data.local.JobsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class JobsDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = JobsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideJobsDao(database: JobsDatabase) = database.getJobsDao()
}