package com.example.partyernewversion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.*
import com.example.partyernewversion.Presenter.profilUsersPresenters.GetUserCurrentPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.IGetUserCurrentPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.IProfilCurrentUser
import com.example.partyernewversion.Presenter.profilUsersPresenters.ProfilCurrentUserPresenter
import com.example.partyernewversion.View.IGetUserCurrentView
import com.example.partyernewversion.View.IProfilCurrentUserView
import com.example.partyernewversion.databinding.FragmentProfilUserBinding
import kotlinx.coroutines.*

class ProfilUserFragment : Fragment(), IProfilCurrentUserView, IGetUserCurrentView {
    lateinit var binding:FragmentProfilUserBinding
    lateinit var profilUserPresenter: IProfilCurrentUser
    lateinit var getUserCurrentPresenter: IGetUserCurrentPresenter
    var userCurr: Users? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilUserBinding.inflate(layoutInflater)
        profilUserPresenter = ProfilCurrentUserPresenter(this)
        getUserCurrentPresenter = GetUserCurrentPresenter(this)

        GlobalScope.launch(Dispatchers.IO) {
            getUserCurrentPresenter.getUserCurrenr()
        }
        binding.exitProfilUser.setOnClickListener {
            profilUserPresenter.exitProfilUser()
        }
        binding.changeLoginProfil.setOnClickListener {
            profilUserPresenter.cangeLoginProfilUser(binding.editChangeLogin.text.toString())
        }
        binding.forDeveloper.setOnClickListener {
            if(userCurr?.phoneNumber == "+79779669296") {
                val testData = TestData()
                testData.uploadDataToDB()
            } else {
                binding.forDeveloper.text = "вы не алина, а лох"
            }
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

    override fun getCurrentUserSuccess(user: Users?) {
        userCurr = user
        binding.phoneNumberUser.text = user?.phoneNumber
        binding.editChangeLogin.hint = user?.userLogin.toString()
    }

    override fun getCurrentUserError(messages: String) {
        Toast.makeText(getActivity(),messages,Toast.LENGTH_SHORT).show();
    }
}