package com.example.partyernewversion.Presenter

import android.util.Log
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.View.IGetUserCurrentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetUserCurrentPresenter(val getUserCurrentView: IGetUserCurrentView): IGetUserCurrentPresenter {
    val mAuth= FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun getUserCurrenr() : Users?{
        ref = FirebaseDatabase.getInstance().reference
        var user: Users? = null
        ref.child("Users").orderByChild("phoneNumber").equalTo(mAuth.currentUser?.phoneNumber.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    user = ds.getValue(Users::class.java)
                    getUserCurrentView.getCurrentUserSuccess(user)
                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                getUserCurrentView.getCurrentUserError("Failed to read value." + error.toException())
            }
        })
        return user
    }
}