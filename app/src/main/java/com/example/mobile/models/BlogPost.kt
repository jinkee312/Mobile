package com.example.mobile.models

data class BlogPost(

    var title: String,

    var body: String,

//    var image: String,

    var username: String // Author of blog post


) {
//    image='$image',
    override fun toString(): String {
        return "BlogPost(title='$title',  username='$username')"
    }


}
























