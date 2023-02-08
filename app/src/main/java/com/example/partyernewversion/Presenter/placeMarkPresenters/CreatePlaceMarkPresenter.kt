package com.example.partyernewversion.Presenter.placeMarkPresenters

import android.icu.text.SimpleDateFormat
import com.example.partyernewversion.Model.PlaceMark
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.View.ICreatePlaceMarkView
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.util.*

class CreatePlaceMarkPresenter(private val placeMarkView: ICreatePlaceMarkView) :
    ICreatePlaceMarkPresenter {
    private val database = FirebaseDatabase.getInstance()
    private var ref = database.reference
    private val HOUR:Long = 3600*1000

    override fun createPlaceMark(name:String, description:String, userCurrent: Users?,latitude: Double, longitude: Double, timeEvent:String, timeRemove:String, hashtagEvent:String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        val text = currentDate + "T" + timeEvent
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val dataTimeEvent:Date = format.parse(text)
        val dataTimeRemove = Date(dataTimeEvent.getTime() + timeRemove.toInt() * HOUR)
        val timestampEvent = Timestamp(dataTimeEvent)
        val timestampRemove = Timestamp(dataTimeRemove)

        val listHashtags = getHashtagList(hashtagEvent)

        val placeMark = PlaceMark(
            null, name, description, userCurrent, latitude, longitude,
            ServerValue.TIMESTAMP, timestampEvent, timestampRemove, listHashtags
        )
        ref = FirebaseDatabase.getInstance().reference.child("PlaceMark")
        ref.push().setValue(placeMark)
        placeMarkView.createPlaceMarkSuccess("Мероприятие добавлено на карту")
    }
    private fun getHashtagList(hashtags: String): List<String?> {
        return hashtags.split("#")
    }
}