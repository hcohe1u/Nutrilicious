package com.exemplys.nutrilicious.data.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListWrapper<T> {
    var list: T? = null  // Navigates down the ‘list’ object
}

@JsonClass(generateAdapter = true)
class ItemWrapper<T> {
    var item: T? = null  // Navigates down the ‘item’ array
}

typealias SearchWrapper<T> = ListWrapper<ItemWrapper<T>>

@JsonClass(generateAdapter = true)

class FoodDto {  // Uses lateinit for properties that must be populated by Moshi
    lateinit var ndbno: String
    lateinit var name: String
    lateinit var group: String
}

