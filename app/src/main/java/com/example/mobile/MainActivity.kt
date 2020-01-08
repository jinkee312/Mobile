package com.example.mobile

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.mobile.adapters.BlogRecyclerAdapter
import com.example.mobile.models.Course
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var coursetable: DatabaseReference
    private lateinit var courseList: MutableList<Course>
    private lateinit var recyclerView: RecyclerView
//    private lateinit var progressBar: ProgressBar
    private var main = R.layout.activity_main

    private lateinit var blogAdapter: BlogRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        progressBar = findViewById(R.id.progressbar)
        coursetable = FirebaseDatabase.getInstance().getReference("Course")
        courseList = mutableListOf()

//        addDataSet()

        btnAdd.setOnClickListener() {
//            initdefaultdata()
            openDialog()
        }
        LoadData()

    }

    fun openDialog(){
        var adddialog:AddDialog
        adddialog = AddDialog()
        adddialog.show(supportFragmentManager, "Add Dialog")
    }

//    private fun addDataSet(){
//        val data = DataSource.createDataSet()
//        blogAdapter.submitList(data)
//    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val itemDeco = DividerItemDecoration(context, VERTICAL)
            addItemDecoration(itemDeco)
            blogAdapter = BlogRecyclerAdapter(courseList, this@MainActivity)
            blogAdapter.notifyDataSetChanged()
            adapter = blogAdapter

        }

    }



    private fun initdefaultdata(){
        val courseid = coursetable.push().key
        val title:String = "Mobile App Development".trim()
        val description:String = "Learn how to do mobile app".trim()
        val username = "Koay Jin Kee"
        val STD = Course(courseid.toString(),title,description,username)
        coursetable.child(courseid.toString()).setValue(STD)

        coursetable.child(courseid.toString()).setValue(STD).addOnCompleteListener{

            Toast.makeText(this,"Successfull", Toast.LENGTH_LONG).show()
//            progressBar.visibility = View.GONE

        }
    }

    private fun savedatatoserver()
    {
        // get value from edit text & spinner

        val title:String = "Mobile App Development".trim()
        val description:String = "Learn how to do mobile app".trim()
        val username = "Koay Jin Kee"

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description))
        {
            val courseid = coursetable.push().key

            val STD = Course(courseid.toString(),title,description,username)
            coursetable.child(courseid.toString()).setValue(STD)

            coursetable.child(courseid.toString()).setValue(STD).addOnCompleteListener{
                Toast.makeText(this,"Successfull", Toast.LENGTH_LONG).show()
            }


        }
        else
        {
            Toast.makeText(this,"Please Enter the name of student", Toast.LENGTH_LONG).show()
        }

    }

    // load data from firebase database
    fun LoadData()
    {

        // show progress bar when call method as loading concept
//        progressBar.visibility = View.VISIBLE
        coursetable.addValueEventListener(object : ValueEventListener {
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
//                    progressBar.visibility = View.GONE
                }
                else
                {
                    // if no data found or you can check specefici child value exist or not here
                    Toast.makeText(applicationContext,"No data Found", Toast.LENGTH_LONG).show()
//                    progressBar.visibility = View.GONE
                }

            }

        })
    }




}