package com.example.partyernewversion.Presenter

import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.Model.Users

interface ICreatePlaceMarkPresenter {
    fun createPlaceMark(name:String, description:String, userCurrent: Users?, latitude: Double, longitude: Double, timeEvent:String, timeRemove:String, hashtagEvent:String)
    fun getPlaceMarkDB()
    fun getInfoPlaceMark(idPlaceMark: String)
}