package com.lr.androidfeature.model

data class Products(
    val cat_id: Int,
    val category_name: String,
    val id: Int,
    val cart_id: Int,
    val offer_discount: String,
    val offer_price: String,
    val price: String,
    val mrp_price: String,
    val discount_price: String,
    val photo: List<String>,
    val slug: String,
    val status: String,
    val title: String,
    val date: String,
    val description: String,
    val reviews: Reviews,
    var productQty: Int = 0,
    var cart_qty: Int,
    val quantity: Int,
    val is_review: Boolean,
    val ratings: Float,
    val amount: Int,
    var total_cart_qty: Int,
    val total_product_qty: Int,
    val discount_type: Int,
    var cart_free_qty: Int,
    var product_qty: Int,
    var ratio: Int,
    var free_product_qty: Int,
    var sequence: Int,
    var product_gst: Int,
    val discount: String,
    val composition: String,
    val product_strips: String,
    val final_price: String,
    val product_type: String,
)

data class Reviews(
    val avg_rating: Float,
    val reviews_data: List<ReviewsData>,
    val total_review: Int
)

data class ReviewsData(
    val rate: Int,
    val review: String,
    val user_name: String
)

