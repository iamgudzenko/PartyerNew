package com.example.partyernewversion.Model

data class PlaceMark(
    val id:String? = null,
    val name:String? = null,
    val description:String? = null,
    val userOwner:Users? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val dateOfCreation:Any? = null,
    val timeEvent: Any? = null,
    val timeRemoveEvent:Any? = null,
    val hashtagsList: List<String?> = emptyList(),
    val listUsersOfJoin:List<Users?> = emptyList()
) {
}