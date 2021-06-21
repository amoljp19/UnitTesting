package com.softaai.unittesting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.softaai.unittesting.model.JobsItemApiResponse
import com.softaai.unittesting.model.LoginUser


@Database(
    entities = [LoginUser::class, JobsItemApiResponse::class],
    version = 1,
    exportSchema = false
)
abstract class JobsDatabase : RoomDatabase() {

    abstract fun getLoginUserDao(): LoginUserDao

    abstract fun getJobsDao(): JobsDao

    companion object {
        const val DB_NAME = "jobs_database"

        @Volatile
        private var INSTANCE: JobsDatabase? = null

        fun getInstance(context: Context): JobsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobsDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}