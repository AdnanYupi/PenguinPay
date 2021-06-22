package com.penguinpay.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.penguinpay.model.LatestRate

@Database(entities = [LatestRate::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDatabaseDao(): AppDatabaseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val lock = Any()

        private lateinit var application: Application

        fun init(application: Application){
            this.application = application
        }

        operator fun invoke() = instance ?: synchronized(lock) {
            instance ?: buildDatabase(application).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "penguin.db"
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}