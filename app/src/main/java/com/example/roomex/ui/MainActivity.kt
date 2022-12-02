package com.example.roomex.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomex.adapter.MemoRecyclerViewAdapter
import com.example.roomex.data.ViewModelFactory
import com.example.roomex.data.model.Memo
import com.example.roomex.data.respository.MemoRepository
import com.example.roomex.data.viewmodel.memo.MemoViewModel
import com.example.roomex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var viewModelFactory: ViewModelFactory
    lateinit var memoViewModel: MemoViewModel

    lateinit var memoList: MutableList<Memo>
    lateinit var memoRecyclerViewAdapter: MemoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initActivity()
    }

    private fun initActivity() {
        initViewModel()
        setUpObserver()
        getAllMemo()
        setUpBtnListener()
    }

    private fun initViewModel() { // ViewModelFactory, ViewModel을 초기화한다.
        viewModelFactory = ViewModelFactory(MemoRepository())
        memoViewModel = ViewModelProvider(this, viewModelFactory).get(MemoViewModel::class.java)
    }

    private fun setUpObserver() { // ViewModel에서의 LiveData들에 대한 Observer를 셋팅해준다.
        memoViewModel.isGetAllMemoComplete.observe(this) {
            memoList = it.toMutableList()
            Log.d("getList::", "size is " + it.size.toString())
            setUpRecyclerView()
        }

        memoViewModel.isMemodeleteComplete.observe(this) {
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            memoList.removeAt(position)
            memoRecyclerViewAdapter.notifyItemRemoved(position)
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }

        memoViewModel.isMemodeleteByIdComplete.observe(this) {
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            if(position != -1) {
                memoList.removeAt(position)
                memoRecyclerViewAdapter.notifyItemRemoved(position)
                memoRecyclerViewAdapter.notifyItemChanged(position)
            }
        }

        memoViewModel.isMemoInsertComplete.observe(this) {
                id ->
            Log.d("insertComplete::", "memo id is $id")
            memoList.add(Memo(id, binding.input.toString(), false))
            binding.input = ""
            memoRecyclerViewAdapter.notifyItemInserted(memoList.size - 1)
        }

        memoViewModel.isEdit.observe(this) {
            binding.isEditing = it.isEdit
            val position = memoList.indexOf(it.memo)

            if(it.isEdit) {
                if(memoRecyclerViewAdapter.lastEditIdx != -1) {
                    memoList[memoRecyclerViewAdapter.lastEditIdx].editMode = false
                    memoRecyclerViewAdapter.notifyItemChanged(memoRecyclerViewAdapter.lastEditIdx)
                }

                memoRecyclerViewAdapter.lastEditIdx = position
                memoList[position].editMode = true
                memoRecyclerViewAdapter.notifyItemChanged(position)
            }

        }

        memoViewModel.isMemoModifyComplete.observe(this) {
            Log.d("modifyComplete::", "memo modified")

            val position = memoList.indexOf(it.memo)

            memoList[position].memo = it.editMemo
            memoList[position].editMode = false

            memoRecyclerViewAdapter.lastEditIdx = -1
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }
    }

    private fun getAllMemo() { // 메모뷰모델을 통해 메모를 가져오게 된다.
        memoViewModel.getAllMemo()
    }
    private fun insertMemo() { //
        if(binding.input.toString().trim().isEmpty().not()) {
            val memo = Memo(0, binding.input.toString(), false)
            memoViewModel.insertMemo(memo)
        }
    }

    private fun setUpRecyclerView() { // 리사이클러뷰 어댑터, 레이아웃 매니저를 초기화하는 메서드이다.
        memoRecyclerViewAdapter = MemoRecyclerViewAdapter(baseContext, memoList, memoViewModel)
        binding.mainRecyclerView.adapter = memoRecyclerViewAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    }

    private fun setUpBtnListener() { //버튼 리스너를 설정하는 메서드.
        binding.inputBtn.setOnClickListener { insertMemo() }
    }
}