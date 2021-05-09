package geekbrains.ru.translator.model.room

import androidx.room.*

//ключевое слово suspend, означает  что все
// запросы в БД будут асинхронными (корутины поддерживаются в Room изначально)
@Dao
interface HistoryDao {
// Получить весь список слов
    @Query ("SELECT*FROM History")
   suspend fun getAll():List<History>
    // Получить конкретное слово
    @Query("SELECT*FROM History WHERE word LIKE :word")
    suspend fun getWord(word:String):History
    // Сохранить новое слово
    // onConflict = OnConflictStrategy.IGNORE означает, что дубликаты не будут сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: History)
    // Сохранить новые слова
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words:List<History>)
    // Обновить слово
    @Update
    suspend fun updateWord(word:History)
    // Удалить слово
    @Delete
    suspend fun deleteWord(word:History)
}