package com.example.partyernewversion.View

import com.example.partyernewversion.Model.Chats

interface ChatsView {
    fun listChatsUser(chat: Chats?)
    fun loadingChatsErrors(message:String)
}