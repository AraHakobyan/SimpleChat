package am.simple.chat.app.chat.view.adapter

import am.simple.chat.R
import am.simple.chat.app.chat.data.model.ChatItem
import am.simple.chat.app.chat.data.model.ChatUiType.Companion.CHAT_TYPE_BY_ME
import am.simple.chat.app.chat.data.model.ChatUiType.Companion.CHAT_TYPE_OTHER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ara Hakobyan on 4/6/2020.
 * Company IDT
 */
class MessagesAdapter(private val userId: Int) : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    var items: ArrayList<ChatItem>? = null

    inner class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView = itemView.findViewById<AppCompatTextView>(R.id.message_item_id)
        private val messageSeenTextView = itemView.findViewById<AppCompatTextView>(R.id.message_item_seen_id)
        fun bindData(item: ChatItem) {
            messageTextView.text = item.message
            messageSeenTextView.visibility = if(item.isSeen) View.VISIBLE else View.GONE
        }
    }

    override fun getItemViewType(position: Int): Int = when (items?.get(position)?.userId) {
        userId -> CHAT_TYPE_BY_ME
        else -> CHAT_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(when(viewType) {
                CHAT_TYPE_OTHER -> R.layout.messages_item_layout_by_me
                else -> R.layout.messages_item_layout_other
            }, parent, false)
                    return MessagesViewHolder(view)
            }

    override fun getItemCount(): Int= items?.size ?: 0

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bindData(items?.get(position) ?: return)
    }

    fun add(item: ChatItem) {
        items?.add(item)
    }
}
