package edu.itvo.pets.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.itvo.pets.core.utils.Converters
import edu.itvo.pets.data.local.daos.PetDao
import edu.itvo.pets.data.local.entities.PetEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Database(entities = [PetEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PetDB : RoomDatabase() {
    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: PetDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PetDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDB::class.java,
                    "pets.dbf"
                )
                    .fallbackToDestructiveMigration() // Esto permite a Room reconstruir la base de datos si la estructura cambia
                    .addCallback(PetDBCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PetDBCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populate(database.petDao())
                    }
                }
            }
        }

        suspend fun populate(petDao: PetDao) {
            petDao.deleteAll()

            val pet = PetEntity(
                id = 1,
                name = "Firulais",
                birthdate = LocalDateTime.now().toString().substring(0,10),
                description = "La mascota favorita. Precargado en la App",
                race = "Labrador",
                type = "Perro",
                image = "@drawable/firulais1",
                isDeleted = false
            )

            val pet2 = PetEntity(
                id = 2,
                name = "Rocky",
                birthdate = LocalDateTime.now().toString().substring(0,10),
                description = "Un pastor alemán muy juguetón y protector",
                race = "Pastor Alemán",
                type = "Perro",
                image = "@drawable/puppy1",
                isDeleted = false
            )
            petDao.insert(pet)
            petDao.insert(pet2)
        }
    }
}