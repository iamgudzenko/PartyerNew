package com.example.partyernewversion.Presenter.profilUsersPresenters

import android.app.Activity
import android.util.Log
import com.example.partyernewversion.View.IPhoneAuthView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumberAuthPresenter (val phoneAuthView: IPhoneAuthView, val activity: Activity):
    IPhoneNumberAuthPresenter {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            phoneAuthView.afterVerification("onVerificationCompleted Success")
            Log.d("GFG" , "onVerificationCompleted Success")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            phoneAuthView.verificationFailed("onVerificationFailed  $e")
            Log.d("GFG" , "onVerificationFailed  $e")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d("GFG","onCodeSent: $verificationId")
            storedVerificationId = verificationId
            resendToken = token
            phoneAuthView.verificationSuccess(storedVerificationId)
        }
    }
    override fun loginPhone(number: String) {
        var phone = number
        if (phone.isNotEmpty()){
            phone = "+7$phone"
            sendVerificationCode(phone)
        }else{
            Log.d("loginPhoneERROR", "Enter mobile number")
            phoneAuthView.loginPhoneError("Enter mobile number")
        }
    }

    override fun sendVerificationCode(number: String) {
        mAuth.setLanguageCode("ru");
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("sendVerificationCode" , "Auth started")
    }
    override fun isSignedIn() {
        val user = mAuth.currentUser
        Log.w("USER", user.toString())
        if (user != null) {
            phoneAuthView.isSignedIn(true)
        } else {
            phoneAuthView.isSignedIn(false)
        }
    }

}