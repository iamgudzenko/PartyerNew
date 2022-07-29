package com.example.partyernewversion.Model

data class Chats(
    var phoneUserChatWith:String? = null,
    var loginUserChatWith:String? = null,
    var listMessages: List<Messages> = emptyList(),
    var countUnreadMess:Int? = 0) {
}