package com.example.partyernewversion.Presenter.profilUsersPresenters

import com.example.partyernewversion.Model.Users

interface IGetUserCurrentPresenter {
    fun getUserCurrenr():Users?
    fun isSignedIn()

}