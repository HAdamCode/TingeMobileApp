package com.example.tinge.presentation.viewmodel

import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State

interface ITingeViewModel {

    val personListState: StateFlow<List<TingePerson>>
    val currentPersonState: StateFlow<TingePerson?>
    val currentUserState: StateFlow<TingePerson?>
    val currentEditProfileState: StateFlow<TingePerson?>

    fun addPerson(personToAdd: TingePerson)

    fun updatePerson(personToUpdate: TingePerson)

    fun checkIfInDB(): Boolean

    fun getRandomProfile()

    fun likePerson(personToLike: TingePerson)

    fun dislikePerson(personToDislike: TingePerson)
}