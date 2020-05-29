package global.msnthrp.scanner.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import global.msnthrp.scanner.db.models.Code
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CodeDao {

    @Query("SELECT * FROM Code ORDER BY time_stamp DESC")
    fun getCodes(): Single<List<Code>>

    @Insert
    fun addCode(code: Code): Single<Long>

    @Delete
    fun removeCode(code: Code): Completable
}