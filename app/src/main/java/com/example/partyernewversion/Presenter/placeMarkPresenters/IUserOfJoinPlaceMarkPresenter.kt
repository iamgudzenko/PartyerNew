package com.example.partyernewversion.Presenter.placeMarkPresenters

interface IUserOfJoinPlaceMarkPresenter {
    fun userOfJoin(phoneNumberUser: String, idPlaceMark: String)
    fun userDeleteOfJoin(phoneNumberUser: String, idPlaceMark: String)
}