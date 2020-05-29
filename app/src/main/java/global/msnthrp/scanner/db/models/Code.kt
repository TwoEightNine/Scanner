package global.msnthrp.scanner.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Code(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val data: String,

    @ColumnInfo(name = "code_type")
    val codeType: String,

    @ColumnInfo(name = "is_scanned")
    val isScanned: Boolean,

    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long = System.currentTimeMillis()

) : Parcelable