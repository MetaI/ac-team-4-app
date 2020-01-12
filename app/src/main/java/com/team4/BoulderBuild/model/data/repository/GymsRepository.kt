package com.team4.BoulderBuild.model.data.repository

import com.team4.BoulderBuild.model.data.source.GymsLocalDataSource
import com.team4.BoulderBuild.model.domain.Gym
import kotlinx.coroutines.runBlocking

class GymsRepository(
    private val gymsLocalDataSource : GymsLocalDataSource
){
    suspend fun getAllGyms() : List<Gym> {
        return gymsLocalDataSource.getAllGyms()
    }

    suspend fun saveGyms(gyms : List<Gym>){
        gymsLocalDataSource.saveGyms(gyms)
    }

    suspend fun findGymById(id: Int) : Gym?{
        return gymsLocalDataSource.findGymById(id)
    }
    suspend fun update(gym: Gym) {
        if(gym.id == null){
            runBlocking {
                gym.id = gymCount() // TODO: improve id management
            }
            saveGyms(listOf(gym))
        }
        else {
            gymsLocalDataSource.update(gym)
        }
    }

    suspend fun gymCount() : Int {
        return gymsLocalDataSource.gymCount()
    }
}