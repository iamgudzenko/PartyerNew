package com.example.partyernewversion.Presenter.placeMarkPresenters

import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.Model.Users

interface ICreatePlaceMarkPresenter {
    fun createPlaceMark(name:String, description:String, userCurrent: Users?, latitude: Double, longitude: Double, timeEvent:String, timeRemove:String, hashtagEvent:String)
}