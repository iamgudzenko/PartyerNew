package com.example.partyernewversion.Presenter

import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.View.ICreatePlaceMarkView
import com.example.partyernewversion.View.IGetUserCurrentView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class CreatePlaceMarkPresenter(val placeMarkView: ICreatePlaceMarkView) : ICreatePlaceMarkPresenter {
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun createPlaceMark(name:String, description:String, userCurrent: Users?,latitude: Double, longitude: Double, timeEvent:String, timeRemove:String, hashtagEvent:String) {
        val placeMark = PlaceMark(null, name, description, userCurrent, latitude, longitude,
            ServerValue.TIMESTAMP,  )
        ref = FirebaseDatabase.getInstance().reference.child("PlaceMark")
        ref.push().setValue(placeMark)
        placeMarkView.createPlaceMarkSuccess("Мероприятие добавлено на карту")
    }

    override fun getPlaceMarkDB() {

    }

    override fun getInfoPlaceMark(idPlaceMark: String) {

    }

}