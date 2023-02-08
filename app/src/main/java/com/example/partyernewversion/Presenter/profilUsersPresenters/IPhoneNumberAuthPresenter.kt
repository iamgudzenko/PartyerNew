package com.example.partyernewversion.Presenter.profilUsersPresenters

interface IPhoneNumberAuthPresenter {
    fun loginPhone(number:String)
    fun sendVerificationCode(number: String)
    fun isSignedIn()
}