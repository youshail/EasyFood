package com.youshail.easyfood.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.youshail.easyfood.data.local.MealInfo
import com.youshail.easyfood.data.local.dao.MealDao

@Database(
    entities = arrayOf(MealInfo::class),
    version = 1
)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealDao() : MealDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MealsDatabase? = null

        fun getDatabase(context: Context): MealsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "meal_data"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}