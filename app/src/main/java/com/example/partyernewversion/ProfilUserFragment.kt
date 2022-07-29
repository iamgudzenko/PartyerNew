package com.example.partyernewversion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.IProfilCurrentUser
import com.example.partyernewversion.Presenter.ProfilCurrentUserPresenter
import com.example.partyernewversion.View.IProfilCurrentUserView
import com.example.partyernewversion.databinding.FragmentProfilUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfilUserFragment : Fragment(), IProfilCurrentUserView {
    lateinit var binding:FragmentProfilUserBinding
    lateinit var profilUserPresenter: IProfilCurrentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilUserBinding.inflate(layoutInflater)
        profilUserPresenter = ProfilCurrentUserPresenter(this)
        CoroutineScope(Dispatchers.IO).launch {
            profilUserPresenter.getProfilUser()
        }
        binding.exitProfilUser.setOnClickListener {
            profilUserPresenter.exitProfilUser()
        }
        binding.changeLoginProfil.setOnClickListener {
            profilUserPresenter.cangeLoginProfilUser(binding.editChangeLogin.text.toString())
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfilUserFragment()

    }

    override fun exitUser() {
        (activity as ProfilCurrentUserActivity?)!!.openFragment(LogInFragment())
    }

    override fun cangeLoginSuccess(message: String) {
        Toast.makeText((activity as ProfilCurrentUserActivity?)!! ,message,Toast.LENGTH_SHORT).show();
    }

    override fun getProfilUserSuccess(userCurrent: Users?) {
        binding.phoneNumberUser.text = userCurrent?.phoneNumber
        binding.editChangeLogin.hint = userCurrent?.userLogin.toString()
    }

    override fun getProfilUserError(message: String) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}