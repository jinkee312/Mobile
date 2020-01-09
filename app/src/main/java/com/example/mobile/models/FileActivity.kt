package com.example.mobile.models

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.AddDialog
import com.example.mobile.R
import com.example.mobile.adapters.BlogRecyclerAdapter
import com.example.mobile.adapters.FileRecycleAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class FileActivity: AppCompatActivity()  {
    private lateinit var fileTable: DatabaseReference
    private lateinit var fileList: MutableList<Course>
    private lateinit var recyclerView: RecyclerView
    //    private lateinit var progressBar: ProgressBar

    private lateinit var blogAdapter: FileRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

//        progressBar = findViewById(R.id.progressbar)
        fileTable = FirebaseDatabase.getInstance().getReference("Course")
            fileList = mutableListOf()

//        addDataSet()

        btnAdd.setOnClickListener() {
            //            initdefaultdata()
            openDialog()
        }
        LoadData()

    }

    fun openDialog(){
        var adddialog: AddDialog
        adddialog = AddDialog()
        adddialog.show(supportFragmentManager, "Add Dialog")
    }

//    private fun addDataSet(){
//        val data = DataSource.createDataSet()
//        blogAdapter.submitList(data)
//    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FileActivity)
            val itemDeco = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(itemDeco)
            blogAdapter = FileRecycleAdapter(fileList, this@FileActivity)
            blogAdapter.notifyDataSetChanged()
            adapter = blogAdapter

        }

    }



    private fun initdefaultdata(){
        val courseid = fileTable.push().key
        val title:String = "Mobile App Development".trim()
        val description:String = "Learn how to do mobile app".trim()
        val username = "Koay Jin Kee"
        val STD = Course(courseid.toString(),title,description,username)
        fileTable.child(courseid.toString()).setValue(STD)

        fileTable.child(courseid.toString()).setValue(STD).addOnCompleteListener{

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
            val courseid = fileTable.push().key

            val STD = Course(courseid.toString(),title,description,username)
            fileTable.child(courseid.toString()).setValue(STD)

            fileTable.child(courseid.toString()).setValue(STD).addOnCompleteListener{
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
        fileTable.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError)
            {
                Toast.makeText(applicationContext,"Error Encounter Due to "+databaseError.message, Toast.LENGTH_LONG).show()/**/

            }

            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    //before fetch we have clear the list not to show duplicate value
                    fileList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children)
                    {
                        val std = data.getValue(Course::class.java)
                        fileList.add(std!!)
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