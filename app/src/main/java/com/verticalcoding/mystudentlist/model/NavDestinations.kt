package com.verticalcoding.mystudentlist.model

import kotlinx.serialization.Serializable

@Serializable
object DogList

@Serializable
data class DogDetailsScreen(val name: String)

@Serializable
data class DogScreen(val name: String)

@Serializable
class DogCreate