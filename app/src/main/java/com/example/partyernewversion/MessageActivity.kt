package com.example.partyernewversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyernewversion.Model.Messages
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.messagesPresenter.IMessagesPresenter
import com.example.partyernewversion.Presenter.messagesPresenter.MessagesPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.GetUserCurrentPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.IGetUserCurrentPresenter
import com.example.partyernewversion.View.IGetUserCurrentView
import com.example.partyernewversion.View.IMessageView
import com.example.partyernewversion.databinding.ActivityMessendgerBinding

class MessageActivity : AppCompatActivity(), IGetUserCurrentView, IMessageView {

    lateinit var getUserCurrentPresenter: IGetUserCurrentPresenter
    lateinit var messagePresenter: IMessagesPresenter
    private lateinit var binding: ActivityMessendgerBinding
    lateinit var adapter: MessageAdapter
    lateinit var loginUserCurrent: String
    var messagesChatUser: MutableList<Messages?> = mutableListOf()
    var userCurr: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessendgerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()
        getUserCurrentPresenter = GetUserCurrentPresenter(this)
        getUserCurrentPresenter.getUserCurrenr()


        val loginUserChatWith = intent.getSerializableExtra("loginUserChatWith").toString()
        loginUserCurrent = intent.getSerializableExtra("loginUserCurrent").toString()
        binding.loginUserToolBar.setText(loginUserChatWith)

        messagePresenter = MessagesPresenter(this)
        messagePresenter.loadingMessages(loginUserChatWith, loginUserCurrent)
        messagePresenter.readingMessages(loginUserChatWith, loginUserCurrent)

        binding.butSendMessage.setOnClickListener {
            messagePresenter.sendMessage(loginUserChatWith, loginUserCurrent, binding.editTextMessage.text.toString())
            binding.editTextMessage.setText("")
        }

    }

    override fun getCurrentUserSuccess(user: Users?) {
        userCurr = user
    }

    override fun getCurrentUserError(messages: String) {
        TODO("Not yet implemented")
    }

    override fun isSignedIn(isSignedIn: Boolean) {
        TODO("Not yet implemented")
    }

    override fun loadingMessagesSuccess(listMessage: MutableList<Messages>) {
        messagesChatUser = listMessage.toMutableList()
        adapter = MessageAdapter()
        adapter.messages = messagesChatUser
        adapter.userCurrentLogin = loginUserCurrent
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMessages.layoutManager = layoutManager
        binding.recyclerViewMessages.adapter = adapter
        binding.recyclerViewMessages.scrollToPosition(adapter.getItemCount() - 1)
    }

    override fun loadingMessagesError(message: String) {
        //Toast.makeText(this@MessageActivity, "Error: $message", Toast.LENGTH_SHORT).show()

    }

    override fun sendMessagesError(message: String) {
        //Toast.makeText(this@MessageActivity, "Error: $message", Toast.LENGTH_SHORT).show()

    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}