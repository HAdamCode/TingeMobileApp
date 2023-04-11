package com.example.tinge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.content.Context
import java.util.*

class TingeViewModel(private val tingeRepo: TingeRepo) : ViewModel(), ITingeViewModel {
    companion object {
        private const val LOG_TAG = "448.TingeViewModel"
    }

    private val mPersons: MutableList<TingePerson> =
        mutableListOf()

    /**
     * holds list of all characters stored within the view model
     */
    private val mPersonListState = MutableStateFlow(mPersons.toList())

    override val personListState: StateFlow<List<TingePerson>>
        get() = mPersonListState.asStateFlow()

    private val mCurrentPersonState: MutableStateFlow<TingePerson?> =
        MutableStateFlow(null)

    override val currentPersonState: StateFlow<TingePerson?>
        get() = mCurrentPersonState

    init {
        Log.d(LOG_TAG, "Characters do be adding")
        addPerson(tingeRepo.persons.first())
        addPerson(tingeRepo.persons.last())
    }

    /**
     * Loads a character by id into currentCharacterState, if it exists.  If id is not found
     * in list of characters, then sets currentCharacterState to null.
     * @param uuid id to use for character lookup
     */
//    override fun loadPersonByUUID(uuid: UUID) {
//        Log.d(LOG_TAG, "loadCharacterByUUID($uuid)")
//        mCurrentCharacterState.value = null
//        mCharacters.forEach { character ->
//            if (character.id == uuid) {
//                Log.d(LOG_TAG, "Character found! $character")
//                mCurrentCharacterState.value = character
//                return
//            }
//        }
//        Log.d(LOG_TAG, "Character not found")
//        return
//    }

    /**
     * Adds the given character to the list of characters.
     * @param characterToAdd character to add to the list
     */
    override fun addPerson(personToAdd: TingePerson) {
        Log.d(LOG_TAG, "adding character $personToAdd")
        mPersonListState.value += personToAdd
        mCurrentPersonState.value = personToAdd
    }

    /**
     * Deletes corresponding character from the list of characters, if it exists in the list.
     * Matches characters by id.  If character is not found in the list, does nothing.
     * @param characterToDelete character to delete from list
     */
//    override fun deleteCharacter(characterToDelete: SamodelkinCharacter) {
//        Log.d(LOG_TAG, "deleting character $characterToDelete")
//        mCharacters.forEach { character ->
//            if (character.id == characterToDelete.id) {
//                mCharacters.remove(character)
//                if (mCurrentCharacterState.value == character) {
//                    mCurrentCharacterState.value = null
//                }
//                Log.d(LOG_TAG, "character deleted")
//                return
//            }
//        }
//        Log.d(LOG_TAG, "Character not found")
//    }

    override fun likePerson(characterToLike: TingePerson) {
        TODO("Not yet implemented")
    }

    override fun dislikePerson(characterToDislike: TingePerson) {
        TODO("Not yet implemented")
    }

}