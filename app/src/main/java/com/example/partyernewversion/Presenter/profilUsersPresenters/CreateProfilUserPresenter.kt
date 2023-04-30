package com.example.partyernewversion.Presenter.profilUsersPresenters

import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.View.ICreateProfilUsserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateProfilUserPresenter(val createProfilUsserView: ICreateProfilUsserView) :
    ICreateProfilUserPresenter {
    var database = FirebaseDatabase.getInstance()
    val mAuth= FirebaseAuth.getInstance()

    override fun writeProfilUserToDB(phoneNumber: String) {
        val user = Users("+7$phoneNumber", mAuth.currentUser?.uid)
        val ref = database.reference.child("Users")
        ref.push().setValue(user)
        createProfilUsserView.createProfilUserSuccess("Успешно зарегистрировались")
    }

}