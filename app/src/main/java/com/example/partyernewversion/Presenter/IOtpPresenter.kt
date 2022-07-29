package com.example.partyernewversion.Presenter

import com.google.firebase.auth.PhoneAuthCredential

interface IOtpPresenter {
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)
}