package com.example.partyernewversion.View

import com.example.partyernewversion.Model.PlaceMark

interface IGetPlaceMarkView {
    fun showPlaceMark(mark: PlaceMark?)
    fun errorGetPlaceMark(message:String)
    fun showInfoPlaceMark(mark: PlaceMark?)
    fun removePlaceMarkOnMap(idPlaceMark: String)
}