package com.example.partyernewversion.Presenter

import android.util.Log
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.View.IProfilCurrentUserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfilCurrentUserPresenter(val profilUserView: IProfilCurrentUserView):IProfilCurrentUser {
    val mAuth= FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun cangeLoginProfilUser(login :String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").orderByChild("phoneNumber").equalTo(mAuth.currentUser?.phoneNumber.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    createNewLogin(ds, login)
                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })
    }
    fun createNewLogin(ds: DataSnapshot, loginNew: String) {
        ds.ref.child("userLogin").setValue(loginNew)
        profilUserView.cangeLoginSuccess("Логин изменен")
    }

    override fun exitProfilUser() {
        mAuth.signOut()
        profilUserView.exitUser()
    }

    override fun getProfilUser() {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").orderByChild("phoneNumber").equalTo(mAuth.currentUser?.phoneNumber.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val user: Users? = ds.getValue(Users::class.java)
                    profilUserView.getProfilUserSuccess(user)
                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                profilUserView.getProfilUserError("Failed to read value." + error.toException())
            }
        })
    }
}