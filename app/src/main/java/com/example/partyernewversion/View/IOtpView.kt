package com.example.partyernewversion.View

interface IOtpView {
    fun signInWithCredentialSuccess(message:String, isNewUser:Boolean)
    fun signInWithCredentialError(message:String)
}