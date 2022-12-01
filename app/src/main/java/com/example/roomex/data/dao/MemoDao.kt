package com.example.roomex.data.dao

import androidx.room.*
import com.example.roomex.data.model.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo): Long // Long을 return하면 해당 memo의 id를 알 수 있다.

    @Delete
    suspend fun deleteMemo(memo: Memo)

    // Memo 테이블의 id와 일치하는 id를 가진 데이터를 삭제한다.
    @Query("DELETE FROM Memo Where id = :id")
    suspend fun deleteMemoByID(id: Long)

    // Memo 테이블의 모든 데이터를 반환한다.
    @Query("SELECT * FROM Memo")
    suspend fun getAllMemo(): List<Memo>

    // Memo 테이블에서 id 필드의 값이 'id'인 모든 레코드의 memo 값을 memo 변경한다.
    @Query("UPDATE Memo SET memo = :memo WHERE id = :id")
    suspend fun modifyMemo(id: Long, memo: String)
}
