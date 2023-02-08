package com.example.partyernewversion.Presenter.profilUsersPresenters

import com.google.firebase.auth.PhoneAuthCredential

interface IOtpPresenter {
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)
}