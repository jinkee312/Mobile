package com.example.mobile.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mobile.R
import com.example.mobile.models.BlogPost
import com.example.mobile.models.Course
import kotlin.collections.ArrayList
import com.google.firebase.database.FirebaseDatabase
//

class BlogRecyclerAdapter (val courseList: List<Course>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val TAG: String = "AppDebug"

//    private var items: List<BlogPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BlogViewHolder -> {
                holder.bind(courseList[position])
                Log.d("sad", "sad")
            }

        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

//    fun submitList(courseList: List<Course>){
//        this.courseList = courseList
//    }

    class BlogViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
//    constructor(
//        itemView: View
//    ): RecyclerView.ViewHolder(itemView){

        //   val blog_image = itemView.blog_image
        val blog_title = itemView.findViewById(R.id.blog_title) as TextView
        val blog_author = itemView.findViewById(R.id.blog_author) as TextView

        fun bind(course: Course){

            blog_title.setText(course.title)
            blog_author.setText(course.username)
        }

    }

}