package com.example.move_application.data.model.response

data class WatchContentListResponse(

    val page:String,
    val results: List<WatchContentResponse>,
    val totle_pages:Int,
    val totle_result:Int,
)
