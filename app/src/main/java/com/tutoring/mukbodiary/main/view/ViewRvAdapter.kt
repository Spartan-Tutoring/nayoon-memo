package com.tutoring.mukbodiary.main.view

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.tutoring.mukbodiary.R
import com.tutoring.mukbodiary.data.db.MemoDB
import com.tutoring.mukbodiary.data.entities.Memo

class ViewRvAdapter(private val context: Context) : RecyclerView.Adapter<ViewRvAdapter.ViewHolder>() {
    val items = ArrayList<Memo>()
    private lateinit var itemClickListner: ItemClickListner

    interface ItemClickListner {
        fun onClick(view: View, position: Int, memo: Memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position, items[position])
        }
    }

    override fun getItemCount(): Int {
        val spf : SharedPreferences = context.getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("bookmarkCnt", items.size)
        editor.apply()

        return items.size
    }

    fun addItems(items: ArrayList<Memo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        notifyDataSetChanged()
    }

    fun setItemClickListner(itemClickListner: ItemClickListner) {
        this.itemClickListner = itemClickListner
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val title: TextView = itemView?.findViewById(R.id.item_view_name_tv)!!
        val review: TextView = itemView?.findViewById(R.id.item_view_review_tv)!!
//        val date: TextView = itemView?.findViewById(R.id.item_date_tv)!!
//        val bookmark: ImageView = itemView?.findViewById(R.id.item_bookmark_iv)!!
//        val copy: ImageView = itemView?.findViewById(R.id.item_copy_iv)!!

        fun bind(item: Memo) {
            title.text = item.title
            review.text = item.review
        }
    }
}