package com.example.partyernewversion.Presenter.messagesPresenter

interface IMessagesPresenter {
    fun loadingMessages(loginUserChatWith:String, loginCurrentUser:String)
    fun sendMessage(loginUserChatWith:String, loginUserOwner:String, textMessage:String)
    fun readingMessages(loginUserChatWith:String, loginCurrentUser:String)
}