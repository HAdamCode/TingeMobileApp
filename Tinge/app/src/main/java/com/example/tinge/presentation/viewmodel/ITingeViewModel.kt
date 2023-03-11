package com.example.tinge.presentation.viewmodel

import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.StateFlow

interface ITingeViewModel {

    val personListState: StateFlow<List<TingePerson>>
    val currentPersonState: StateFlow<TingePerson?>

    fun addPerson(characterToAdd: TingePerson)

    fun likePerson(characterToLike: TingePerson)

    fun dislikePerson(characterToDislike: TingePerson)
}