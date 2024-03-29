package com.example.tinge.presentation.viewmodel

import com.example.tinge.data.TingeMessages
import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.StateFlow

interface ITingeViewModel {

    val personListState: StateFlow<List<TingePerson>>
    val currentPersonState: StateFlow<TingePerson?>
    val currentUserState: StateFlow<TingePerson?>
    val currentEditProfileState: StateFlow<TingePerson?>
    val currentMessagesListState: StateFlow<List<TingeMessages>>
    val chatListState: StateFlow<List<TingePerson>>
    val currentPersonChatState: StateFlow<TingePerson?>
    val currentImageState: StateFlow<String>

    fun addPerson(personToAdd: TingePerson)

    fun updatePerson(personToUpdate: TingePerson, image: String)

    fun checkIfInDB(): Boolean

    fun getRandomProfile()

    fun getCurrentChatList(person: TingePerson)
    fun getChatPersonList()

    fun sendMessage(message: TingeMessages)

    fun addMatch(person: TingePerson, likeDislike: Boolean)

    fun updateImage(imageToUpdate: String)
}