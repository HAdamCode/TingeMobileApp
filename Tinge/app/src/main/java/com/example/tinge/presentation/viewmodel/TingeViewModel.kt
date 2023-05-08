package com.example.tinge.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.lifecycle.ViewModel
import com.example.tinge.R
import com.example.tinge.data.TingeMatches
import com.example.tinge.data.TingeMessages
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import java.io.ByteArrayOutputStream
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

    private val mCurrentUserState: MutableStateFlow<TingePerson?> =
        MutableStateFlow(null)

    override val currentUserState: StateFlow<TingePerson?>
        get() = mCurrentUserState

    private val mCurrentEditProfileState: MutableStateFlow<TingePerson?> =
        MutableStateFlow(null)

    override val currentEditProfileState: StateFlow<TingePerson?>
        get() = mCurrentEditProfileState

    private val mCurrentMessagesListState: MutableStateFlow<List<TingeMessages>> =
        MutableStateFlow(emptyList())

    override val currentMessagesListState: StateFlow<List<TingeMessages>>
        get() = mCurrentMessagesListState

    private val mChatListState: MutableStateFlow<List<TingePerson>> =
        MutableStateFlow(emptyList())

    override val chatListState: StateFlow<List<TingePerson>>
        get() = mChatListState

    private val mCurrentPersonChatState: MutableStateFlow<TingePerson?> =
        MutableStateFlow(null)

    override val currentPersonChatState: StateFlow<TingePerson?>
        get() = mCurrentPersonChatState



    private val mCurrentImageState: MutableStateFlow<String> =
        MutableStateFlow("")
    override val currentImageState: StateFlow<String>
        get() = mCurrentImageState.asStateFlow()

    init {
        Log.d(LOG_TAG, "Characters do be adding")
//        addPerson(tingeRepo.persons.first())
//        addPerson(tingeRepo.persons.last())
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        getRandomProfile()
//        if (userEmail != null) {
//            Log.d("TingeViewModel", userEmail)
//        }
//        else {
//            Log.d("TingeViewModel", "No email")
//        }
        collectionRef.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
//                if (documents.size() == 0) {
//                    mCurrentUserState.update { null }
//                }
                Log.d("TingeViewModel", "Inside viewmodel for loop")
                if (documents.documents.isEmpty()) {
                    Log.d("TingeViewModel", "This happened 1")
                    val data = TingePerson(
                        firstName = "",
                        lastName = "",
                        imageId = "",
                        age = 0,
                        height = 0,
                        gender = "",
                        email = userEmail,
                        preference = "",
                        lat = 0.0,
                        lon = 0.0
                    )
                    val documentRef = collectionRef.document()
                    documentRef.set(data)
                }
                for (document in documents) {
                    if(document.get("imageId") is String){
                        Log.d(LOG_TAG, "This one happens")
                        mCurrentUserState.update { document.toObject(TingePerson::class.java) }
                    }else{
                        val firstName = document.get("firstName").toString()
                        val lastName = document.get("lastName").toString()
                        val imageId = ""
                        val age = Integer.parseInt(document.get("age").toString())
                        val height = Integer.parseInt(document.get("height").toString())
                        val gender = document.get("gender").toString()
                        val email = document.get("email").toString()
                        val preference = document.get("preference").toString()
                        var lat = document.getDouble("lat")
                        var lon = document.getDouble("lon")
                        if (lat == null)
                            lat = 0.0
                        if (lon == null)
                            lon = 0.0
                        val tempTingePerson : TingePerson = TingePerson(firstName, lastName, imageId, age, height, gender, email, preference,lat,lon)
                        mCurrentUserState.update { tempTingePerson }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }

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

    override fun updateImage(imageToUpdate: String) {
        Log.d(LOG_TAG, "Image Updated!")
        Log.d(LOG_TAG, imageToUpdate.length.toString())
        mCurrentImageState.update { imageToUpdate }
    }

    /**
     * Adds the given character to the list of characters.
     * @param characterToAdd character to add to the list
     */
    override fun addPerson(personToAdd: TingePerson) {
        Log.d(LOG_TAG, "adding character $personToAdd")
//        mPersonListState.value += personToAdd
//        mCurrentPersonState.value = personToAdd
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        val data = TingePerson(
            firstName = "Hunter",
            lastName = "Adam",
            imageId = "",
            age = 21,
            height = 63,
            gender = "Male",
            email = userEmail,
            preference = "Non-Binary",
            lat = 0.0,
            lon = 0.0
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

    override fun updatePerson(tingePerson: TingePerson, image: String) {
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        val query = collectionRef.whereEqualTo("email", tingePerson.email)
        val updates = mapOf<String, Any>(
            "firstName" to tingePerson.firstName,
            "lastName" to tingePerson.lastName,
            "imageId" to image,
            "age" to tingePerson.age,
            "height" to tingePerson.height,
            "gender" to tingePerson.gender,
            "preference" to tingePerson.preference
        )

        query.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                Log.d("TingeViewsssssModel", document.get("firstName").toString())
                collectionRef.document(document.id).update(updates)
            }
        }.addOnFailureListener { exception ->
            Log.d("ViewModel", "Error updating documents: $exception")
        }
    }

    override fun checkIfInDB(): Boolean {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        var numberOfDocs = true
//        userEmail?.let { Log.d("TingeViewModel", it) }
        collectionRef.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val document = querySnapshot.documents.first()
                if(document.get("imageId") is String){
                    mCurrentUserState.value = querySnapshot.documents.first().toObject(TingePerson::class.java)
                }else{
                    val firstName = document.get("firstName").toString()
                    val lastName = document.get("lastName").toString()
                    val imageId = ""
                    val age = Integer.parseInt(document.get("age").toString())
                    val height = Integer.parseInt(document.get("height").toString())
                    val gender = document.get("gender").toString()
                    val email = document.get("email").toString()
                    val preference = document.get("preference").toString()
                    var lat = document.getDouble("lat")
                    var lon = document.getDouble("lon")
                    if (lat == null)
                        lat = 0.0
                    if (lon == null)
                        lon = 0.0
                    val tempTingePerson : TingePerson = TingePerson(firstName, lastName, imageId, age, height, gender, email, preference,lat,lon)
                    mCurrentUserState.value = tempTingePerson
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
//        Log.d("TingeViewsssssModel", numberOfDocs.toString())
        return numberOfDocs
    }

    override fun getRandomProfile() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        var collectionRef = db.collection("Matches")
        val listOfMatches: MutableStateFlow<List<String>> =
            MutableStateFlow(emptyList())
        var preference = "Male"
        val currentUser = mCurrentUserState.value
        if (currentUser != null) {
            preference = currentUser.preference
        }
        collectionRef.whereEqualTo("currentuser", userEmail.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listOfMatches.value += document.toObject(TingeMatches::class.java).otheruser
                }
                Log.d("preference", preference)
                collectionRef = db.collection("TingePerson")
                collectionRef.whereEqualTo("gender", preference)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val tingePerson: TingePerson
                            if (document.get("imageId") is String) {
                                tingePerson = document.toObject(TingePerson::class.java)
                            } else {
                                val firstName = document.get("firstName").toString()
                                val lastName = document.get("lastName").toString()
                                val imageId = ""
                                val age = Integer.parseInt(document.get("age").toString())
                                val height = Integer.parseInt(document.get("height").toString())
                                val gender = document.get("gender").toString()
                                val email = document.get("email").toString()
                                val preference = document.get("preference").toString()
                                var lat = document.getDouble("lat")
                                var lon = document.getDouble("lon")
                                if (lat == null)
                                    lat = 0.0
                                if (lon == null)
                                    lon = 0.0
                                val tempTingePerson: TingePerson = TingePerson(
                                    firstName,
                                    lastName,
                                    imageId,
                                    age,
                                    height,
                                    gender,
                                    email,
                                    preference,
                                    lat,
                                    lon
                                )
                                tingePerson = tempTingePerson
                            }
                            if (tingePerson.email in listOfMatches.value) {
                                continue
                            }
                            if (userEmail != tingePerson.email) {
                                mCurrentPersonState.update { tingePerson }
                                break
                            }
                        }
                    }
            }
    }

    override fun getChatPersonList() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        var collectionRef = db.collection("Matches")
        mChatListState.value = emptyList()
        collectionRef.whereEqualTo("currentuser", userEmail.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val chat = document.toObject(TingeMatches::class.java)
                    if (chat.liked) {
                        collectionRef.whereEqualTo("currentuser", chat.otheruser)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    val newChat = document.toObject(TingeMatches::class.java)

                                    if (newChat.liked) {
                                        collectionRef = db.collection("TingePerson")
                                        collectionRef.whereEqualTo("email", chat.otheruser).get()
                                            .addOnSuccessListener { documents ->
                                                for (document in documents) {
                                                    val tingePerson: TingePerson
                                                    if (document.get("imageId") is String) {
                                                        tingePerson = document.toObject(TingePerson::class.java)
                                                    } else {
                                                        val firstName = document.get("firstName").toString()
                                                        val lastName = document.get("lastName").toString()
                                                        val imageId = ""
                                                        val age = Integer.parseInt(document.get("age").toString())
                                                        val height = Integer.parseInt(document.get("height").toString())
                                                        val gender = document.get("gender").toString()
                                                        val email = document.get("email").toString()
                                                        val preference = document.get("preference").toString()
                                                        var lat = document.getDouble("lat")
                                                        var lon = document.getDouble("lon")
                                                        if (lat == null)
                                                            lat = 0.0
                                                        if (lon == null)
                                                            lon = 0.0
                                                        val tempTingePerson: TingePerson = TingePerson(
                                                            firstName,
                                                            lastName,
                                                            imageId,
                                                            age,
                                                            height,
                                                            gender,
                                                            email,
                                                            preference,
                                                            lat,
                                                            lon
                                                        )
                                                        tingePerson = tempTingePerson
                                                    }
                                                    if (tingePerson in mChatListState.value)
                                                        continue
                                                    mChatListState.value += tingePerson
                                                    break
                                                }
                                            }

                                    }
                                }
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    override fun getCurrentChatList(person: TingePerson) {
        mCurrentPersonChatState.value = person
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("Messages")
        Log.d("Tinge View Model", userEmail.toString())
        Log.d("Tinge View Model", person.email.toString())
        mCurrentMessagesListState.value = emptyList()
        collectionRef.whereEqualTo("sender", userEmail.toString())
            .whereEqualTo("receiver", person.email)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("Tinge View Model", "Sucessssssss")
                for (document in documents) {
                    mCurrentMessagesListState.value += document.toObject(TingeMessages::class.java)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
        collectionRef.whereEqualTo("sender", person.email)
            .whereEqualTo("receiver", userEmail.toString())
            .get()
            .addOnSuccessListener { documents ->
                Log.d("Tinge View Model", "Sucessssssss")
                for (document in documents) {
                    mCurrentMessagesListState.value += document.toObject(TingeMessages::class.java)
                }
                mCurrentMessagesListState.value =
                    mCurrentMessagesListState.value.sortedBy { it.timestamp }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    override fun sendMessage(messages: TingeMessages) {
        val db = Firebase.firestore
        val collectionRef = db.collection("Messages")
        val documentRef = collectionRef.document()
        documentRef.set(messages)
    }

    override fun addMatch(person: TingePerson, likeDislike: Boolean) {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("Matches")

        collectionRef.whereEqualTo("currentuser", userEmail)
            .whereEqualTo("otheruser", person.email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.size == 0) {
                    val documentRef = collectionRef.document()
                    if (userEmail != null && person.email != null) {
                        val match = TingeMatches(userEmail, person.email, likeDislike)
                        documentRef.set(match)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    override fun likePerson(characterToLike: TingePerson) {
        TODO("Not yet implemented")
    }

    override fun dislikePerson(characterToDislike: TingePerson) {
        TODO("Not yet implemented")
    }
}