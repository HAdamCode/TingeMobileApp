package com.example.tinge.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tinge.data.TingeMatches
import com.example.tinge.data.TingeMessages
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TingeViewModel(tingeRepo: TingeRepo) : ViewModel(), ITingeViewModel {
    companion object {
        private const val LOG_TAG = "448.TingeViewModel"
    }

    private val mPersons: MutableList<TingePerson> = mutableListOf()

    private val mPersonListState = MutableStateFlow(mPersons.toList())

    override val personListState: StateFlow<List<TingePerson>>
        get() = mPersonListState.asStateFlow()

    private val mCurrentPersonState: MutableStateFlow<TingePerson?> = MutableStateFlow(null)

    override val currentPersonState: StateFlow<TingePerson?>
        get() = mCurrentPersonState

    private val mCurrentUserState: MutableStateFlow<TingePerson?> = MutableStateFlow(null)

    override val currentUserState: StateFlow<TingePerson?>
        get() = mCurrentUserState

    private val mCurrentEditProfileState: MutableStateFlow<TingePerson?> = MutableStateFlow(null)

    override val currentEditProfileState: StateFlow<TingePerson?>
        get() = mCurrentEditProfileState

    private val mCurrentMessagesListState: MutableStateFlow<List<TingeMessages>> =
        MutableStateFlow(emptyList())

    override val currentMessagesListState: StateFlow<List<TingeMessages>>
        get() = mCurrentMessagesListState

    private val mChatListState: MutableStateFlow<List<TingePerson>> = MutableStateFlow(emptyList())

    override val chatListState: StateFlow<List<TingePerson>>
        get() = mChatListState

    private val mCurrentPersonChatState: MutableStateFlow<TingePerson?> = MutableStateFlow(null)

    override val currentPersonChatState: StateFlow<TingePerson?>
        get() = mCurrentPersonChatState


    private val mCurrentImageState: MutableStateFlow<String> = MutableStateFlow("")
    override val currentImageState: StateFlow<String>
        get() = mCurrentImageState.asStateFlow()

    init {
        Log.d(LOG_TAG, "Characters do be adding")
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        getRandomProfile()
        Log.d("TTTTTTTTTT", userEmail.toString())
        collectionRef.whereEqualTo("email", userEmail).get().addOnSuccessListener { documents ->
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
                mCurrentImageState.update { "" }
                val documentRef = collectionRef.document()
                documentRef.set(data)
            }
            for (document in documents) {
                if (document.get("imageId") is String) {
                    Log.d(LOG_TAG, "This one happens")
                    val tingePerson =  document.toObject(TingePerson::class.java)
                    mCurrentUserState.update { tingePerson }
                    mCurrentImageState.update { tingePerson.imageId }
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
                    if (lat == null) lat = 0.0
                    if (lon == null) lon = 0.0
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
                    mCurrentUserState.update { tempTingePerson }
                }
            }
        }.addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }

    }

    override fun updateImage(imageToUpdate: String) {
        Log.d(LOG_TAG, "Image Updated!")
        mCurrentImageState.update { imageToUpdate }
    }

    override fun addPerson(personToAdd: TingePerson) {
        Log.d(LOG_TAG, "adding character $personToAdd")
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

    override fun updatePerson(tingePerson: TingePerson, image: String) {
        val db = Firebase.firestore
        val collectionRef = db.collection("TingePerson")
        val query = collectionRef.whereEqualTo("email", tingePerson.email)
        val updates = mapOf<String, Any>(
            "firstName" to tingePerson.firstName,
            "lastName" to tingePerson.lastName,
            "imageId" to mCurrentImageState.value,
            "age" to tingePerson.age,
            "height" to tingePerson.height,
            "gender" to tingePerson.gender,
            "preference" to tingePerson.preference,
            "lat" to tingePerson.lat,
            "lon" to tingePerson.lon
        )

        query.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
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
        val numberOfDocs = true
        collectionRef.whereEqualTo("email", userEmail).get().addOnSuccessListener { querySnapshot ->
            if (querySnapshot.documents.size != 0) {
                val document = querySnapshot.documents.first()
                if (document.get("imageId") is String) {
                    mCurrentUserState.value =
                        querySnapshot.documents.first().toObject(TingePerson::class.java)
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
                    if (lat == null) lat = 0.0
                    if (lon == null) lon = 0.0
                    val tempTingePerson = TingePerson(
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
                    mCurrentUserState.value = tempTingePerson
                }
            } else {
                mCurrentUserState.value = TingePerson(email = userEmail)
                val documentRef = collectionRef.document()
                documentRef.set(TingePerson(email = userEmail))

            }
        }.addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }
        return numberOfDocs
    }

    override fun getRandomProfile() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val db = Firebase.firestore
        var collectionRef = db.collection("Matches")
        val listOfMatches: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
        var preference = "Male"
        val currentUser = mCurrentUserState.value
        if (currentUser != null) {
            preference = currentUser.preference
        }
        collectionRef.whereEqualTo("currentuser", userEmail.toString()).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listOfMatches.value += document.toObject(TingeMatches::class.java).otheruser
                }
                Log.d("preference", preference)
                collectionRef = db.collection("TingePerson")
                collectionRef.whereEqualTo("gender", preference).get()
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
                                if (lat == null) lat = 0.0
                                if (lon == null) lon = 0.0
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
        collectionRef.whereEqualTo("currentuser", userEmail.toString()).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val chat = document.toObject(TingeMatches::class.java)
                    if (chat.liked) {
                        collectionRef.whereEqualTo("currentuser", chat.otheruser).get()
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
                                                        tingePerson =
                                                            document.toObject(TingePerson::class.java)
                                                    } else {
                                                        val firstName =
                                                            document.get("firstName").toString()
                                                        val lastName =
                                                            document.get("lastName").toString()
                                                        val imageId = ""
                                                        val age = Integer.parseInt(
                                                            document.get("age").toString()
                                                        )
                                                        val height = Integer.parseInt(
                                                            document.get("height").toString()
                                                        )
                                                        val gender =
                                                            document.get("gender").toString()
                                                        val email = document.get("email").toString()
                                                        val preference =
                                                            document.get("preference").toString()
                                                        var lat = document.getDouble("lat")
                                                        var lon = document.getDouble("lon")
                                                        if (lat == null) lat = 0.0
                                                        if (lon == null) lon = 0.0
                                                        val tempTingePerson = TingePerson(
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
                                                    if (tingePerson in mChatListState.value) continue
                                                    mChatListState.value += tingePerson
                                                    break
                                                }
                                            }

                                    }
                                }
                            }
                    }
                }
            }.addOnFailureListener { exception ->
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
            .whereEqualTo("receiver", person.email).get().addOnSuccessListener { documents ->
                Log.d("Tinge View Model", "Sucessssssss")
                for (document in documents) {
                    mCurrentMessagesListState.value += document.toObject(TingeMessages::class.java)
                }
            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
        collectionRef.whereEqualTo("sender", person.email)
            .whereEqualTo("receiver", userEmail.toString()).get()
            .addOnSuccessListener { documents ->
                Log.d("Tinge View Model", "Sucessssssss")
                for (document in documents) {
                    mCurrentMessagesListState.value += document.toObject(TingeMessages::class.java)
                }
                mCurrentMessagesListState.value =
                    mCurrentMessagesListState.value.sortedBy { it.timestamp }
            }.addOnFailureListener { exception ->
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

        collectionRef.whereEqualTo("currentuser", userEmail).whereEqualTo("otheruser", person.email)
            .get().addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.size == 0) {
                    val documentRef = collectionRef.document()
                    if (userEmail != null && person.email != null) {
                        val match = TingeMatches(userEmail, person.email, likeDislike)
                        documentRef.set(match)
                    }
                }
            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }
}