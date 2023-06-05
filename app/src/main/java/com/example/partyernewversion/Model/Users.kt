package com.example.partyernewversion.Model

data class Users(
    val phoneNumber:String? = null,
    val userLogin:String? = null,
    var listPlaceMarkOfJoin: List<String> = listOf()
) {
}