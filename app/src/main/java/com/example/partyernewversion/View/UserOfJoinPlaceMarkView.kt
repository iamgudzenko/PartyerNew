package com.example.partyernewversion.View

interface UserOfJoinPlaceMarkView {
    fun ofJoinSuccessListPlaceMark(countUserJoin: Int)
    fun ofJoinErrorListPlaceMark(message: String)
    fun ofJoinSuccessListUser(listOfJoinUser: MutableList<String>)
    fun ofJoinErrorListUser(message: String)
    fun deleteJoinListSuccessPlaceMark(countUserJoin: Int)
    fun deleteJoinSuccessListUser(listOfJoinUser: MutableList<String>)
}