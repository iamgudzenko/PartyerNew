package com.example.partyernewversion.Presenter.placeMarkPresenters

import android.util.Log
import com.example.partyernewversion.Model.Messages
import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.messagesPresenter.IMessagesPresenter
import com.example.partyernewversion.Presenter.messagesPresenter.MessagesPresenter
import com.example.partyernewversion.View.IMessageView
import com.example.partyernewversion.View.UserOfJoinPlaceMarkView
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import java.util.*

class UserOfJoinPlaceMarkPresenter(private val userOfJoinView: UserOfJoinPlaceMarkView): IUserOfJoinPlaceMarkPresenter {
    private val database = FirebaseDatabase.getInstance()
    private var ref = database.reference

    override fun userOfJoin(phoneNumberUser: String, idPlaceMark: String) {
        var user:Users? = null
        var mark:PlaceMark? = null
        ref = FirebaseDatabase.getInstance().reference
        ref.child("PlaceMark").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    if(ds.key.equals(idPlaceMark)) {
                        mark = ds.getValue(PlaceMark::class.java)
                        val l = mark?.listUsersOfJoin?.toMutableList()
                        addListUserPlaceMark(ds, l, phoneNumberUser)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })

        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").orderByChild("phoneNumber").equalTo(phoneNumberUser).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    user = ds.getValue(Users::class.java)
                    Log.w("JINUser", user?.phoneNumber.toString())
                    val l = user?.listPlaceMarkOfJoin?.toMutableList()

                    addListPlaceMarkInuser(ds, l, idPlaceMark)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                userOfJoinView.ofJoinErrorListUser(error.toException().toString())
            }
        })
    }

    override fun userDeleteOfJoin(phoneNumberUser: String, idPlaceMark: String) {
        var user:Users? = null
        var mark:PlaceMark? = null
        ref = FirebaseDatabase.getInstance().reference
        ref.child("PlaceMark").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    if(ds.key.equals(idPlaceMark)) {
                        mark = ds.getValue(PlaceMark::class.java)
                        val l = mark?.listUsersOfJoin?.toMutableList()
                        deleteListUserPlaceMark(ds, l, phoneNumberUser)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })

        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").orderByChild("phoneNumber").equalTo(phoneNumberUser).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    user = ds.getValue(Users::class.java)
                    Log.w("JINUser", user?.phoneNumber.toString())
                    val l = user?.listPlaceMarkOfJoin?.toMutableList()
                    deleteListPlaceMarkInuser(ds, l, idPlaceMark)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                userOfJoinView.ofJoinErrorListUser(error.toException().toString())
            }
        })
    }

    fun addListUserPlaceMark(ds: DataSnapshot, l: MutableList<String>?, phoneNumberUser: String) {
        l?.add(phoneNumberUser)
        ds.ref.child("listUsersOfJoin").setValue(l)
        userOfJoinView.ofJoinSuccessListPlaceMark(l!!.size)
    }

    fun addListPlaceMarkInuser(ds: DataSnapshot, l: MutableList<String>?, id: String) {
        l?.add(id)
        ds.ref.child("listPlaceMarkOfJoin").setValue(l)
        userOfJoinView.ofJoinSuccessListUser(l!!)
    }

    fun deleteListUserPlaceMark(ds: DataSnapshot, l: MutableList<String>?, phoneNumberUser: String) {
        l?.remove(phoneNumberUser)
        ds.ref.child("listUsersOfJoin").setValue(l)
        userOfJoinView.deleteJoinListSuccessPlaceMark(l!!.size)
    }

    fun deleteListPlaceMarkInuser(ds: DataSnapshot, l: MutableList<String>?, id: String) {
        l?.remove(id)
        ds.ref.child("listPlaceMarkOfJoin").setValue(l)
        userOfJoinView.deleteJoinSuccessListUser(l!!)
    }

}
