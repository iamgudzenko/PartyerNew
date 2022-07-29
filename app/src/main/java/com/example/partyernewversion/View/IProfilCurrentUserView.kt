package com.example.partyernewversion.View

import com.example.partyernewversion.Model.Users

interface IProfilCurrentUserView {
    fun exitUser()
    fun cangeLoginSuccess(message: String)
    fun getProfilUserSuccess(userCurrent: Users?)
    fun getProfilUserError(message:String)
}