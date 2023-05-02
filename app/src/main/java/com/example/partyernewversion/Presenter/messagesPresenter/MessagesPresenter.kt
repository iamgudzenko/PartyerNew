package com.example.partyernewversion.Presenter.messagesPresenter

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.partyernewversion.Model.Chats
import com.example.partyernewversion.Model.Messages
import com.example.partyernewversion.View.IMessageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessagesPresenter(val messageView: IMessageView) : IMessagesPresenter {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun loadingMessages(loginUserChatWith: String, loginCurrentUser: String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Chats").child(loginCurrentUser).orderByChild("loginUserChatWith").equalTo(loginUserChatWith).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.children.count() == 0) {
                    messageView.loadingMessagesError("Empty!")
                }
                for (ds in dataSnapshot.children) {
                    val chats: Chats? = ds.getValue(Chats::class.java)
                    messageView.loadingMessagesSuccess(chats?.listMessages as MutableList<Messages>)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageView.loadingMessagesError(error.toString())
            }

        })
    }

    override fun sendMessage(
        phoneUserChatWith: String,
        phoneUserOwner: String,
        textMessage: String
    ) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Chats").child(phoneUserOwner).orderByChild("loginUserChatWith").equalTo(phoneUserChatWith).addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.childrenCount.toInt() == 0) {
                    val newChatCurrentUser = Chats(null, phoneUserOwner, mutableListOf(Messages(phoneUserOwner, textMessage, ServerValue.TIMESTAMP)), 1)
                    val newChatWithUser = Chats(null, phoneUserChatWith, mutableListOf(Messages(phoneUserOwner, textMessage, ServerValue.TIMESTAMP)))

                    ref.child("Chats").child(phoneUserChatWith).push().setValue(newChatCurrentUser)
                    ref.child("Chats").child(phoneUserOwner).push().setValue(newChatWithUser)
                }
                Log.e("sendNewChats", dataSnapshot.childrenCount.toString())
                for (ds in dataSnapshot.children) {
                    val chats: Chats? = ds.getValue(Chats::class.java)
                    val messages = Messages(phoneUserOwner, textMessage, ServerValue.TIMESTAMP)
                    val c = chats?.listMessages?.size ?: 0
                    var countUnreadMess:Int? = chats?.countUnreadMess
                    addChatstoDb(phoneUserChatWith, phoneUserOwner, textMessage)
                    writeChats(messages, c, ds, countUnreadMess)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageView.sendMessagesError(error.toString())
            }

        })
    }
    fun writeChats(messages: Messages, c: Int?, ds:DataSnapshot, countUnreadMess:Int?){
        ds.ref.child("countUnreadMess").setValue(countUnreadMess)
        ds.ref.child("listMessages").child(c.toString()).setValue(messages)
    }
    fun addChatstoDb(loginUserChatWith:String, loginUserOwner: String, textMessage: String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Chats").child(loginUserChatWith).orderByChild("loginUserChatWith").equalTo(loginUserOwner).addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val chats: Chats? = ds.getValue(Chats::class.java)
                    var countUnreadMess:Int? = chats?.countUnreadMess?.plus(1)
                    val messages = Messages(loginUserOwner, textMessage, ServerValue.TIMESTAMP)
                    val c = chats?.listMessages?.size ?: 0
                    writeChats(messages, c, ds, countUnreadMess)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageView.sendMessagesError(error.toString())
            }

        })
    }


    override fun readingMessages(loginUserChatWith: String, loginCurrentUser: String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Chats").child(loginCurrentUser).orderByChild("loginUserChatWith").equalTo(loginUserChatWith).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.children.count() == 0) {

                } else {
                    for (ds in dataSnapshot.children) {
                        ds.ref.child("countUnreadMess").setValue(0)

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                messageView.loadingMessagesError(error.toString())
            }

        })
    }
}