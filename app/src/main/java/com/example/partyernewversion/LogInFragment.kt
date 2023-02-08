package com.example.partyernewversion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.partyernewversion.Presenter.profilUsersPresenters.*
import com.example.partyernewversion.View.ICreateProfilUsserView
import com.example.partyernewversion.View.IOtpView
import com.example.partyernewversion.View.IPhoneAuthView
import com.example.partyernewversion.databinding.FragmentLogInBinding
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.*


class LogInFragment : Fragment(), IPhoneAuthView, IOtpView, ICreateProfilUsserView {
    lateinit var binding:FragmentLogInBinding
    lateinit var phonePresenter: IPhoneNumberAuthPresenter
    lateinit var otpPresenter: IOtpPresenter
    lateinit var createProfilUserPresenter: ICreateProfilUserPresenter
    lateinit var storedVerification: String

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(layoutInflater)
        phonePresenter = PhoneNumberAuthPresenter(this, (activity as ProfilCurrentUserActivity?)!!)
        otpPresenter = OtpPresenter(this, (activity as ProfilCurrentUserActivity?)!!)
        createProfilUserPresenter = CreateProfilUserPresenter(this)
        GlobalScope.launch(Dispatchers.IO) {
            phonePresenter.isSignedIn()
        }

        binding.butSendSms.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                phonePresenter.loginPhone(binding.editNumber.text.toString())
            }
            binding.inputPhoneNumber.visibility = View.GONE
            binding.otpInput.visibility = View.VISIBLE
        }

        binding.butOtpExam.setOnClickListener {
            val smsCode = binding.editOtp.text.toString()
            if(smsCode.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerification, smsCode)
                otpPresenter.signInWithPhoneAuthCredential(credential)
            }else{

            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogInFragment()
    }

    override fun verificationSuccess(storedVerificationId: String) {
        storedVerification = storedVerificationId
    }

    override fun loginPhoneError(message: String) {
        binding.textInfoPhone.text = message
    }

    override fun afterVerification(message: String) {
//        (activity as ProfilCurrentUserActivity?)!!.openFragment(ProfilUserFragment())
        Log.w("FUN", "afterVerification")
    }

    override fun verificationFailed(message: String) {
        binding.textInfoPhone.text = message
    }

    override fun isSignedIn(isSignedIn: Boolean) {
        if(isSignedIn){
            (activity as ProfilCurrentUserActivity?)!!.openFragment(ProfilUserFragment())
            Log.w("FUN", "isSignedIn")
        }
    }

    override fun signInWithCredentialSuccess(message: String, isNewUser: Boolean) {
        if(isNewUser) {
            createProfilUserPresenter.writeProfilUserToDB(binding.editNumber.text.toString())
        }
        (activity as ProfilCurrentUserActivity?)!!.openFragment(ProfilUserFragment())
        Log.w("FUN", "signInWithCredentialSuccess")
    }

    override fun signInWithCredentialError(message: String) {
        binding.textInfoOtp.text = message
    }

    override fun createProfilUserSuccess(message: String) {
        binding.textInfoOtp.text = message
    }

    override fun createProfilUserError(message: String) {
        binding.textInfoOtp.text = message
    }
}