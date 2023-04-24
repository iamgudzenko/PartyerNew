package com.example.partyernewversion.Presenter.messagesPresenter

import android.util.Log
import com.example.partyernewversion.Model.Chats
import com.example.partyernewversion.View.ChatsView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatsPresenter(val chatsView: ChatsView): IChatsPresenter {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun loadingChats(phoneCurrentUser: String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Chats").child(phoneCurrentUser).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val chat: Chats? = ds.getValue(Chats::class.java)
                    chatsView.listChatsUser(chat)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                chatsView.loadingChatsErrors("Failed to read value." + error.toException())
            }
        })
    }
}