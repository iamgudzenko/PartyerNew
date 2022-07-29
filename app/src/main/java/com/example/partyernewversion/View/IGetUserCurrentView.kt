package com.example.partyernewversion.View

import com.example.partyernewversion.Model.Users

interface IGetUserCurrentView {
    fun getCurrentUserSuccess(user:Users?)
    fun getCurrentUserError(messages: String)
}