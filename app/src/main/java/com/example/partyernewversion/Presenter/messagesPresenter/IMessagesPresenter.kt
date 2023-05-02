package com.example.partyernewversion.Presenter.messagesPresenter

interface IMessagesPresenter {
    fun loadingMessages(loginUserChatWith:String, loginCurrentUser:String)
    fun sendMessage(phoneUserChatWith: String, phoneUserOwner:String, textMessage:String)
    fun readingMessages(loginUserChatWith:String, loginCurrentUser:String)
}