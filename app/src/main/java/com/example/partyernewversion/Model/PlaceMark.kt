package com.example.partyernewversion.Model

import com.google.firebase.database.Exclude
import com.google.firebase.Timestamp

data class PlaceMark(
    var id:String? = null,
    val name:String? = null,
    val description:String? = null,
    val userOwner:Users? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val dateOfCreation:Any? = null,
    var timeEvent: Any? = null,
    var timeRemoveEvent:Any? = null,
    val hashtagsList: List<String?> = emptyList(),
    val listUsersOfJoin: List<String> = listOf("hi"),
) {


//    var createdTimestampTimeEvent:Any? = timeEvent
//        @Exclude
//        get() {
//            if(timeEvent == null){
//                return 0
//            } else {
//                return timeEvent as Long
//            }
//        }
    var createdTimestampTimeRemove:Any? = timeRemoveEvent
        @Exclude
        get() {
            return if(timeRemoveEvent == null){
                0
            } else {
                timeRemoveEvent as Timestamp
            }
        }
}