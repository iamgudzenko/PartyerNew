package com.example.partyernewversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.partyernewversion.Presenter.*
import com.example.partyernewversion.View.IOtpView
import com.example.partyernewversion.View.IPhoneAuthView
import com.example.partyernewversion.databinding.ActivityProfilUserBinding
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class ProfilCurrentUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(LogInFragment())

    }
    fun openFragment(f: Fragment){
        val fmanag = supportFragmentManager
        val ftr = fmanag.beginTransaction()
        ftr.replace(R.id.profilUserFragm, f)
        ftr.commit()
    }
}