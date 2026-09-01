package com.example.giveu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "giveu_database"
            )

                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}
