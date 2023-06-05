package com.example.partyernewversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.partyernewversion.Presenter.TestData
import com.example.partyernewversion.databinding.ActivityProfilUserBinding

class ProfilCurrentUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val testData = TestData()
//        testData.uploadDataToDB()
        openFragment(LogInFragment())

    }
    fun openFragment(f: Fragment){
        val fmanag = supportFragmentManager
        val ftr = fmanag.beginTransaction()
        ftr.replace(R.id.profilUserFragm, f)
        ftr.commit()
    }
}