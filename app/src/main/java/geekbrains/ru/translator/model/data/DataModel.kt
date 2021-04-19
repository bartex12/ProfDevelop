package geekbrains.ru.translator.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataModel(
    @Expose val text: String?,
    @Expose val meanings: List<Meanings>?
):Parcelable
