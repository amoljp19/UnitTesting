package com.softaai.unittesting.di.module

import com.softaai.unittesting.data.repository.DefaultJobsRepository
import com.softaai.unittesting.data.repository.DefaultLoginUserRepository
import com.softaai.unittesting.data.repository.JobsRepository
import com.softaai.unittesting.data.repository.LoginUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class JobsRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindJobsRepository(jobsRepository: DefaultJobsRepository): JobsRepository


    @ActivityRetainedScoped
    @Binds
    abstract fun bindLoginUserRepository(loginUserRepository: DefaultLoginUserRepository): LoginUserRepository

}