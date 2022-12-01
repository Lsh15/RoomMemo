package com.example.roomex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// 메모장 정보를 표현하는 Entity를 정의한다.
// Entity 이름과 동일한 Memo 테이블이 생성된다.
@Entity
class Memo (
    // id를 주요 키로 사용한다.
    // id를 자동으로 지정
    @PrimaryKey(autoGenerate = true) var id: Long,
    var memo: String,
    var editMode: Boolean
)