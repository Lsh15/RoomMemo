package com.example.roomex.data.dao

import androidx.room.*
import com.example.roomex.data.model.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo): Long // Long을 return하면 해당 memo의 id를 알 수 있다.

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("DELETE FROM Memo Where id = :id")
    suspend fun deleteMemoByID(id: Long)

    @Query("SELECT * FROM Memo")
    suspend fun getAllMemo(): List<Memo>

    @Query("UPDATE Memo SET memo = :memo WHERE id = :id")
    suspend fun modifyMemo(id: Long, memo: String)
}
