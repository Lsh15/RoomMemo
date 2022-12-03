package com.example.roomex.data.viewmodel.memo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomex.data.model.Memo
import com.example.roomex.data.respository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel(private val memoRepository: MemoRepository): ViewModel() {
    val isEdit = MutableLiveData<EditMemoPostData>() // 수정 모드로 진입하기 위해 사용되는 메서드.changeMode() 메서드와 연동된다.
    val isMemoInsertComplete = MutableLiveData<Long>() // insertMemo() 메서드와 연동된다.

    val isMemodeleteComplete = MutableLiveData<Memo>() // deleteMemo() 메서드와 연동된다.
    val isMemodeleteByIdComplete = MutableLiveData<Memo>() //  deleteMemoById() 메서드와 연동된다.
    val isMemoModifyComplete = MutableLiveData<EditMemoPostData>() // modifyMemo() 메서드와 연동된다.

    val isGetAllMemoComplete = MutableLiveData<List<Memo>>() // getAllMemo() 메서드와 연동된다.

    fun changeMode(memo: Memo, _isEdit: Boolean) { // 수정모드로 진입한다.
        CoroutineScope(Dispatchers.IO).launch {
            isEdit.postValue(EditMemoPostData(memo, memo.memo, _isEdit))
        }
    }

    fun insertMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.insertMemo(memo).let {
                    id ->
                isMemoInsertComplete.postValue(id)
            }
        }
    }

    fun deleteMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemo(memo).let {
                isMemodeleteComplete.postValue(memo)
            }
        }
    }

    fun deleteMemoById(memo: Memo) { // 메모를 삭제해 주고, 어댑터의 리스트에서도 자체적으로 삭제해준다.
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemoByID(memo.id).let {
                isMemodeleteByIdComplete.postValue(memo)
            }
        }
    }

    fun modifyMemo(memo: Memo, editMemo: String) { // 해당 메모를 수정해준다.
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.modifyMemo(memo.id, editMemo).let {
                isMemoModifyComplete.postValue(EditMemoPostData(memo, editMemo, false))
            }
        }
    }

    fun getAllMemo() {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.getAllMemo().let {
                isGetAllMemoComplete.postValue(it)
            }
        }
    }

    inner class EditMemoPostData(val memo: Memo, val editMemo: String, val isEdit: Boolean)
}