package geekbrains.ru.translator.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Translation(
    @Expose val text: String?
):Parcelable
