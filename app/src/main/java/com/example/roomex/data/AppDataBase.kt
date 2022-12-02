package com.example.roomex.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomex.data.dao.MemoDao
import com.example.roomex.data.model.Memo

// RoomDatabase를 상속하는 추상 클래스로 룸 데이터베이스 클래스를 선언한다.
// @Database 어노테이션으로 룸 데이터베이스의 속성을 지정한다.
// entities에 데이터베이스에서 사용할 엔티티의 클래스를 배열 형태로 넣어주며,
// version에 데이터베이스의 버전을 넣어준다.
@Database(entities = [Memo::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    // 메모 정보에 접근하는 MemoDao 데이터 접근 객체를
    // Room Database인 AppDataBase와 연결한다.
    abstract fun memoDao(): MemoDao
}