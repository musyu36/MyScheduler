package com.example.myscheduler

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    //Realmクラスのプロパティ、後にonCreateメソッドで初期化するためlateinit
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Realmクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()

        list.layoutManager = LinearLayoutManager(this)
        val schedules = realm.where<Schedule>().findAll()
        val adapter = ScheduleAdapter(schedules)
        list.adapter = adapter

        //ScheduleEditActivityに画面遷移
        fab.setOnClickListener { view ->
            val intent = Intent(this, ScheduleEditActivity::class.java)
            startActivity(intent)
        }

        //RecyclerViewがクリックされたときのコールバック処理
        //RecyclerViewがクリックされたときのアダプターに用意したsetOnItemClickListenerメソッドに登録
        adapter.setOnItemClickListener{id ->
            val intent = Intent(this , ScheduleEditActivity::class.java)
                //Scheduleのidを受け取りインテントにschedule_idとして格納することでidをスケジュール編集用アクティビティScheduleEditActivityに渡す
                .putExtra("schedule_id" , id)
            startActivity(intent)

        }
    }

    //アクティビティの終了処理
    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }


}
