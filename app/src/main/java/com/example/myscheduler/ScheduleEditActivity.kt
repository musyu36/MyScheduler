package com.example.myscheduler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)

        //Realmインスタンスを取得
        realm = Realm.getDefaultInstance()

        //保存処理
        //SAMインターフェイスが入れ子構造になっているので暗黙の引数itがわかるようviewとdbと引数名を指定
        save.setOnClickListener{ view: View ->
            //データベースへの書き込み時に必要なトランザクションを使用,トランザクションの開始、終了、キャンセル処理を自動で行う
            realm.executeTransaction{db: Realm ->
                //Schedule内の最大idを取得
                val maxId = db.where<Schedule>().max("id")
                //最大id+1を新規idとして登録する(エルビス演算子?: でmaxIdがnullならLong型の0+1を返す,それ以外はLongに変換して+1して格納)
                val nextId = (maxId?.toLong() ?: 0L) + 1
                //データを1行追加,帰ってくるScheduleインスタンス(新規データ)の各フィールドに値を設定すればデータの追加が完了
                val schedule = db.createObject<Schedule>(nextId)

                //TextEditに入力された日付を表す文字列をDate型の値に変換してから格納
                val date = dateEdit.text.toString().toDate("yyyy/MM/dd")
                if(date != null) schedule.date = date
                schedule.title = titleEdit.text.toString()
                schedule.detail = detailEdit.text.toString()
            }
            //スナックバーの設定
            //スタティックメソッドmakeで作成し、showで表示
            Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                //第２引数listenerに渡すView.OnClickListenerはSAM変換によりラムダ式で記述
                .setAction("戻る"){finish()}
                .setActionTextColor(Color.YELLOW)
                .show()
        }
    }

    //終了処理
    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }

    private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date?{
        return try{
            SimpleDateFormat(pattern).parse(this)
        } catch(e: IllegalArgumentException){
            return null
        }catch(e: ParseException){
            return null
        }
    }
}
