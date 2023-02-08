package com.example.partyernewversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.partyernewversion.databinding.ItemHashtagBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface HashtagsActionListener{
    fun goToHashtagMark(hashtag:String?)
}

class HashtagListAdapter(
    private val actionListener: HashtagsActionListener
    ): RecyclerView.Adapter<HashtagListAdapter.HashtagViewHolder>(), View.OnClickListener {
    var hashtags: List<String?> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    class HashtagViewHolder(val binding: ItemHashtagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HashtagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHashtagBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return HashtagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HashtagViewHolder, position: Int) {
        val hashtag = hashtags[position]
        with(holder.binding) {
            holder.itemView.tag = hashtag
            textHashtag.text = "#$hashtag"
        }

    }

    override fun getItemCount() = hashtags.size

    override fun onClick(p0: View) {
        val hashtag = p0.tag as String
        actionListener.goToHashtagMark(hashtag)

    }
}