package geekbrains.ru.translator.model.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(History::class), version = 1,exportSchema = false)
abstract class HistoryDataBase :RoomDatabase() {

    abstract fun historyDao() : HistoryDao

    companion object{
        const val DB_NAME = "HistoryDB.db"
    }

}