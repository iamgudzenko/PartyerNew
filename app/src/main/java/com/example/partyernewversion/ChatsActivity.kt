package com.example.partyernewversion

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyernewversion.Model.Chats
import com.example.partyernewversion.Model.Users
import com.example.partyernewversion.Presenter.messagesPresenter.ChatsPresenter
import com.example.partyernewversion.Presenter.messagesPresenter.IChatsPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.GetUserCurrentPresenter
import com.example.partyernewversion.Presenter.profilUsersPresenters.IGetUserCurrentPresenter
import com.example.partyernewversion.View.ChatsView
import com.example.partyernewversion.View.IGetUserCurrentView
import com.example.partyernewversion.databinding.ActivityChatsBinding

class ChatsActivity : AppCompatActivity(), ChatsView, IGetUserCurrentView {
    private lateinit var binding: ActivityChatsBinding
    private lateinit var adapter: ChatsAdapter
    lateinit var chatsPresenter: IChatsPresenter
    lateinit var getUserCurrentPresenter: IGetUserCurrentPresenter
    var userProfilCurrent: Users? = null
    var chatsMap: MutableMap<String?, Chats?> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chatsPresenter = ChatsPresenter(this)

        getUserCurrentPresenter = GetUserCurrentPresenter(this)
        getUserCurrentPresenter.getUserCurrenr()

        adapter = ChatsAdapter(object : ChatsActionListener {
            override fun goToMessages(chat: Chats) {
                Log.w("LoginUser", chat.loginUserChatWith.toString())
                val intent = Intent(this@ChatsActivity, MessageActivity::class.java)
                intent.putExtra("loginUserChatWith", chat.loginUserChatWith.toString())
                intent.putExtra("loginUserCurrent", userProfilCurrent?.userLogin.toString())
                startActivity(intent)
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listChatsUser(chat: Chats?) {
        if(chatsMap.containsKey(chat?.loginUserChatWith)){
            chatsMap.remove(chat?.loginUserChatWith)
            chatsMap[chat?.loginUserChatWith] = chat
        } else {
            chatsMap[chat?.loginUserChatWith] = chat
        }
        adapter.chats = chatsMap.values.toMutableList()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChats.layoutManager = layoutManager
        binding.recyclerViewChats.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        chatsMap.clear()
        binding.recyclerViewChats.removeAllViews()
        chatsPresenter.loadingChats(userProfilCurrent?.userLogin ?: " ")
        super.onResume()
    }

    override fun loadingChatsErrors(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
    fun updateChats(){
        chatsMap.clear()
        binding.recyclerViewChats.removeAllViews()
        chatsPresenter.loadingChats(userProfilCurrent?.userLogin.toString())
    }
    override fun getCurrentUserSuccess(user: Users?) {
        userProfilCurrent = user
        updateChats()
    }

    override fun getCurrentUserError(messages: String) {
        TODO("Not yet implemented")
    }

    override fun isSignedIn(isSignedIn: Boolean) {
        TODO("Not yet implemented")
    }

}