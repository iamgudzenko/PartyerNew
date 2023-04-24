package com.example.partyernewversion.View

import com.example.partyernewversion.Model.Messages


interface IMessageView {
    fun loadingMessagesSuccess(listMessage: MutableList<Messages>)
    fun loadingMessagesError(message:String)
    fun sendMessagesError(message:String)
}