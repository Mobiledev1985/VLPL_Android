package com.lr.androidfeature.model

data class Articles(
    val photo: List<String>,
    val slug: String,
    val summary: String,
    val title: String,
    val date: String,
    val description: String,
)
