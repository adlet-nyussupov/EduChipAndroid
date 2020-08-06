package com.moniumverse.educhip_app.model.opportunities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moniumverse.educhip_app.model.user.UserDatabase


@Database(entities = arrayOf(OpportunitiesModel::class), version = 1)
abstract class OpportunitiesDatabase : RoomDatabase() {

    abstract fun OpportunitiesDAO(): OpportunitiesDAO

    companion object {
        @Volatile
        private var instance: OpportunitiesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            OpportunitiesDatabase::class.java,
            "opportunitiesdatabase"
        ).build()

    }


}