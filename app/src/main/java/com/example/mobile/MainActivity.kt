package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.adapters.BlogRecyclerAdapter
import com.example.mobile.models.Course
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var studentdatabase: DatabaseReference
    private lateinit var courseList: MutableList<Course>
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var blogAdapter: BlogRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressbar)
        studentdatabase = FirebaseDatabase.getInstance().getReference("Course")
        courseList = mutableListOf()

//        addDataSet()
        btnAdd.setOnClickListener(){
            initdefaultdata()
            progressBar.visibility = View.VISIBLE

        }
        LoadData()

    }

//    private fun addDataSet(){
//        val data = DataSource.createDataSet()
//        blogAdapter.submitList(data)
//    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            blogAdapter = BlogRecyclerAdapter(courseList, this@MainActivity)
            adapter = blogAdapter
//            adapter.notifyDataSetChanged()
        }
    }

    private fun initdefaultdata(){
        val courseid = studentdatabase.push().key
        val title:String = "Mobile App Development".trim()
        val description:String = "Learn how to do mobile app".trim()
        val username = "Koay Jin Kee"
        val STD = Course(courseid.toString(),title,description,username)
        studentdatabase.child(courseid.toString()).setValue(STD)

        studentdatabase.child(courseid.toString()).setValue(STD).addOnCompleteListener{

            Toast.makeText(this,"Successfull", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.GONE

    }
    }

    // load data from firebase database
    private fun LoadData()
    {

        // show progress bar when call method as loading concept
        progressBar.visibility = View.VISIBLE

        studentdatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError)
            {
                Toast.makeText(applicationContext,"Error Encounter Due to "+databaseError.message, Toast.LENGTH_LONG).show()/**/

            }

            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    //before fetch we have clear the list not to show duplicate value
                    courseList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children)
                    {
                        val std = data.getValue(Course::class.java)
                        courseList.add(std!!)
                    }

                    // bind data to adapter
                    initRecyclerView()
                    progressBar.visibility = View.GONE


                }
                else
                {
                    // if no data found or you can check specefici child value exist or not here
                    Toast.makeText(applicationContext,"No data Found", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }

            }

        })
    }




}

























