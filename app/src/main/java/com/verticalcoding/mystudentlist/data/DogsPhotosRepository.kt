package com.verticalcoding.mystudentlist.data

import com.verticalcoding.mystudentlist.data.local.database.DogEntity
import com.verticalcoding.mystudentlist.data.local.database.DogEntityDao
import com.verticalcoding.mystudentlist.data.network.DogsService
import com.verticalcoding.mystudentlist.model.Dog
import com.verticalcoding.mystudentlist.model.DogPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DogsRepository {
    val dogs: Flow<List<Dog>>
    suspend fun getRandomDogImage(): DogPhoto
    suspend fun add(name: String, breed: String, imageURL: String)
    suspend fun remove(id: Int)
    suspend fun triggerFav(id: Int)
}

class DefaultDogsRepository(
    private val dogsService: DogsService,
    private val dogDao: DogEntityDao
) : DogsRepository {

    override val dogs: Flow<List<Dog>> = dogDao.getSortedDogs().map { items ->
        items.map {
            Dog(
                id = it.uid,
                name = it.name,
                breed = it.breed,
                imageURL = it.imageURL,
                isFavorite = it.isFav,
            )
        }
    }

    override suspend fun getRandomDogImage(): DogPhoto = dogsService.getRandomDogImage()

    override suspend fun add(name: String, breed: String, imageURL: String) {
        dogDao.insertDog(DogEntity(name = name,breed = breed, imageURL = imageURL, isFav = false))
    }

    override suspend fun remove(id: Int) {
        dogDao.removeDog(id)
    }

    override suspend fun triggerFav(id: Int) {
        dogDao.triggerFavDog(id)
    }
}