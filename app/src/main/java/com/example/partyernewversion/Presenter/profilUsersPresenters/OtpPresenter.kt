package com.example.partyernewversion.Presenter.profilUsersPresenters

import android.app.Activity
import android.util.Log
import com.example.partyernewversion.View.IOtpView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential

class OtpPresenter(val otpView: IOtpView, val activity: Activity): IOtpPresenter {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                val user = task.result.additionalUserInfo?.isNewUser
                if (task.isSuccessful) {
                    Log.w("ISNEWUSER", "$user")
                    otpView.signInWithCredentialSuccess("signInWithPhoneAuthCredentialSuccess", user!!)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        otpView.signInWithCredentialError(task.exception.toString())
                    }
                }
            }
    }
}