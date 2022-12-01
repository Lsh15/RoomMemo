package com.example.roomex.data.respository

import com.example.roomex.common.GlobalApplication
import com.example.roomex.data.model.Memo

class MemoRepository {
    private val appDBInstance = GlobalApplication.appDataBaseInstance.memoDao()

    suspend fun insertMemo(memo: Memo) = appDBInstance.insertMemo(memo)
    suspend fun deleteMemo(memo: Memo) = appDBInstance.deleteMemo(memo)
    suspend fun deleteMemoByID(id: Long) = appDBInstance.deleteMemoByID(id)
    suspend fun getAllMemo() = appDBInstance.getAllMemo()
    suspend fun modifyMemo(id: Long, memo: String) = appDBInstance.modifyMemo(id, memo)
}