package com.example.ivcare.patientDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class PatientDatabase: RoomDatabase() {

    abstract val patientDao: PatientDao

    companion object {

        @Volatile
        private var INSTANCE: PatientDatabase? = null

        fun getInstance(context: Context): PatientDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PatientDatabase::class.java,
                        "patient_display_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}