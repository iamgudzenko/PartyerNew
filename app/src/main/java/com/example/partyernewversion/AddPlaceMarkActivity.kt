package com.example.partyernewversion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.placeMarkPresenters.CreatePlaceMarkPresenter
import com.example.partyernewversion.Presenter.placeMarkPresenters.ICreatePlaceMarkPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.GetUserCurrentPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.IGetUserCurrentPresenter
import com.example.partyernewversion.View.ICreatePlaceMarkView
import com.example.partyernewversion.View.IGetUserCurrentView
import com.example.partyernewversion.databinding.ActivityAddPlaceMarkBinding


class AddPlaceMarkActivity : AppCompatActivity(), ICreatePlaceMarkView, IGetUserCurrentView {
    lateinit var binding: ActivityAddPlaceMarkBinding
    lateinit var createPlaceMarkPresenter: ICreatePlaceMarkPresenter
    lateinit var getUserCurrentPresenter: IGetUserCurrentPresenter
    var userCurrent: Users? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var spinerrText = arrayOf("Выбрать", "Моё местоположение")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createPlaceMarkPresenter = CreatePlaceMarkPresenter(this)
        getUserCurrentPresenter = GetUserCurrentPresenter(this)
        getUserCurrentPresenter.getUserCurrenr()
        val arguments = intent.extras

        if (arguments!!.getBoolean("isMapTapPoint")) {
            latitude = arguments.getDouble("latitudeTapPoint")
            longitude = arguments.getDouble("longitudeTapPoint")
            spinerrText[0] = "Точка на карте"
        } else {
            latitude = arguments.getDouble("userLatitude")
            longitude = arguments.getDouble("userLongitude")
        }


        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinerrText)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Получаем выбранный объект
                    val item = parent.getItemAtPosition(position) as String
                    if (item == spinerrText[1]) {
                        latitude = arguments.getDouble("userLatitude")
                        longitude = arguments.getDouble("userLongitude")
                    }
//                    else if (item == spinerrText[2]) {
//                    //Переход на выбор точки
////                        val intent = Intent(this@AddNewPlaceMark, PointSelectionMap::class.java)
////                        startActivity(intent)
//                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.spinner.onItemSelectedListener = itemSelectedListener

        binding.buttonCreatePlace.setOnClickListener {
            if (binding.editNameMark.text.toString().isNotEmpty()
                && binding.editDesctiption.text.toString().isNotEmpty()
                && binding.editTimeEvent.text.toString().isNotEmpty()
                && binding.editTimeRemoveEvent.text.toString().isNotEmpty()
            ) {
                if (binding.editTimeEvent.text.toString().matches(Regex("([01]?[0-9]|2[0-3]):[0-5][0-9]"))) {
                    if (binding.editTimeRemoveEvent.text.toString().toInt() in 1..24) {
                        createPlaceMarkPresenter.createPlaceMark(binding.editNameMark.text.toString(),
                        binding.editDesctiption.text.toString(),
                        userCurrent,
                        latitude,
                        longitude,
                        binding.editTimeEvent.text.toString(),
                        binding.editTimeRemoveEvent.text.toString(),
                        binding.editHashtags.text.toString())
                    } else {
                        binding.editTimeRemoveEvent.error = "от 1 до 24 ч"
                    }

                } else {
                    binding.editTimeEvent.error = "неккоректное время"
                }
            } else {
                binding.textView.text = "не оставляйте поля пустыми"
            }
        }

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun createPlaceMarkSuccess(message: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        finish()
    }

    override fun getCurrentUserSuccess(user: Users?) {
        userCurrent = user
    }

    override fun getCurrentUserError(messages: String) {

    }

    override fun isSignedIn(isSignedIn: Boolean) {
        TODO("Not yet implemented")
    }
}