package com.lr.androidfeature.model

class BaseModel<T>(
    val status: Int, val message: String, val result: HashMap<String, T>
)