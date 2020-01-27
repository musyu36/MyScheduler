package com.example.myscheduler

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

//データを格納するモデル
open class Schedule : RealmObject(){
    //idは一意である必要があるためPrimaryKeyアノテーションを付与
    @PrimaryKey
    var id: Long = 0
    var date: Date = Date()
    var title: String = ""
    var detail: String = ""
}