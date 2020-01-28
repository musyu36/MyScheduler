package com.example.myscheduler

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>):
    RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true) {

    //引数がLong?型で戻り値がない(Unit)関数型の変数listenerを宣言
    //変数listenerは後に値を設定するのでNull許容型にする、関数型の全体をカッコで囲み、?をつけることでNull許容((Long?)->Unit)?
    private var listener: ((Long?) -> Unit)? = null

    //関数を引数に受け取りlistenerに格納する、関数を格納する変数listenerに関数をセットする
    fun setOnItemClickListener(listener: (Long?)-> Unit){
        this.listener = listener
    }
    init{
        setHasStableIds(true)
    }

    //セルに使用するビューを保持する
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell){
        val date: TextView =  cell.findViewById(android.R.id.text1)
        val title: TextView = cell.findViewById(android.R.id.text2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule: Schedule? = getItem(position)
        holder.date.text = DateFormat.format("yyyy/MM/dd", schedule?.date)
        holder.title.text = schedule?.title

        //セルに使用しているビューがクリックされたときのイベント,invokeは関数型の変数を実行するためのメソッド,引数に関数型で定義した引数を渡す,今回はID
        holder.itemView.setOnClickListener{
            listener?.invoke(schedule?.id)
        }
    }

    override fun getItemId(position: Int): Long{
        return getItem(position)?.id ?: 0
    }
}