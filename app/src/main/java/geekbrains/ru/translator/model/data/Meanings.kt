package geekbrains.ru.translator.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Meanings(
    @Expose val translation: Translation?,
    @Expose  val imageUrl: String?,
    @Expose  val transcription: String?,
    @Expose  val soundUrl: String?
):Parcelable
