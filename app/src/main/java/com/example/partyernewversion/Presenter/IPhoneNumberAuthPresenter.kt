package com.example.partyernewversion.Presenter

interface IPhoneNumberAuthPresenter {
    fun loginPhone(number:String)
    fun sendVerificationCode(number: String)
    fun isSignedIn()
}