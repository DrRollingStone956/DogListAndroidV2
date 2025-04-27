package com.verticalcoding.mystudentlist.model

import kotlinx.serialization.Serializable

@Serializable
object DogList

@Serializable
data class DogDetailsScreen(val name: String, val breed: String, val imageURL: String,val id: Int)

@Serializable
object DogCreate