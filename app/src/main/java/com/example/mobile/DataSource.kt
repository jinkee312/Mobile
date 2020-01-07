package com.example.mobile

import com.example.mobile.models.BlogPost
import com.example.mobile.models.Course

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<Course>{
            val list = ArrayList<Course>()
            list.add(
                Course(
                    "Congratulations!",
                    "",
                    "You made it to the end of the course!\r\n\r\nNext we'll be building the REST API!",
//                    "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",,
                    "Sally"

                    )
            )

            return list
        }
    }
}