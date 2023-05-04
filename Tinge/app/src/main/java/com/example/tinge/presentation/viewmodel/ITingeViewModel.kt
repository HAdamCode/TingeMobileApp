package com.example.tinge.presentation.viewmodel

import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.StateFlow

interface ITingeViewModel {

    val personListState: StateFlow<List<TingePerson>>
    val currentPersonState: StateFlow<TingePerson?>

    fun addPerson(personToAdd: TingePerson)

    fun updatePerson(email: String?)

    fun checkIfInDB(): Boolean

    fun likePerson(personToLike: TingePerson)

    fun dislikePerson(personToDislike: TingePerson)
}