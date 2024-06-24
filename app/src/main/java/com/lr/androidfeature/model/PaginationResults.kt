package com.lr.androidfeature.model

import com.google.gson.annotations.SerializedName

data class PaginationResults<P>(
    val current_page: Int,
    val first_page_url: String,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val prev_page_url: String,
    @SerializedName("article_data", alternate = ["product_data"])
    val list: List<P>
)