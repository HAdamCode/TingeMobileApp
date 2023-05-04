package com.example.tinge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.math.floor

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
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        val data = TingePerson(
            firstName = "Hunter",
            lastName = "Adam",
            imageId = 123,
            age = 21,
            height = 63,
            gender = "Male",
            email = userEmail
        )
        val documentRef = collectionRef.document()
        documentRef.set(data)
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

    override fun updatePerson(email: String?) {

    }

    override fun checkIfInDB(): Boolean {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        var numberOfDocs = 0
        collectionRef.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                numberOfDocs = documents.size()
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
        return numberOfDocs > 0
    }

    override fun likePerson(characterToLike: TingePerson) {
        TODO("Not yet implemented")
    }

    override fun dislikePerson(characterToDislike: TingePerson) {
        TODO("Not yet implemented")
    }
}