package com.example.myscheduler

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Realmクラスのプロパティ、後にonCreateメソッドで初期化するためlateinit
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Realmクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    //アクティビティの終了処理
    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }


}
