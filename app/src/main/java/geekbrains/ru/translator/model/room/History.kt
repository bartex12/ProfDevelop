package geekbrains.ru.translator.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// достаточно написать поля в конструкторе
// класса. В качестве основной ячейки мы используем ячейку "слово", то есть
// слово, которое мы искали и хотим сохранить. unique = true означает, что
// в БД не будут сохраняться повторяющиеся слова.
@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class History (
    @field:PrimaryKey
    @field: ColumnInfo(name = "word") var word:String,  //ключ
    @field: ColumnInfo(name = "description") var description:String?
)