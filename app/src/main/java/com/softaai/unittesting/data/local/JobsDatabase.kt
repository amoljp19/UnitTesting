package com.softaai.unittesting.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class JobsDatabase : RoomDatabase() {

    abstract fun getJobsDao() : JobsDao

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