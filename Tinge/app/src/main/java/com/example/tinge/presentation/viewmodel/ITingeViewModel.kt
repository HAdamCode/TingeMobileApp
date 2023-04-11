package com.example.tinge.presentation.viewmodel

import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.StateFlow
import java.util.*

interface ITingeViewModel {

    val personListState: StateFlow<List<TingePerson>>
    val currentPersonState: StateFlow<TingePerson?>

    fun loadPersonByUUID(uuid: UUID)

    fun addPerson(personToAdd: TingePerson)

    fun likePerson(personToLike: TingePerson)

    fun dislikePerson(personToDislike: TingePerson)
}