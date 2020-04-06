package am.simple.chat.app.chat.view.adapter

import am.simple.chat.R
import am.simple.chat.app.chat.data.model.ChatItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ara Hakobyan on 4/6/2020.
 * Company IDT
 */
class MessagesAdapter: RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    var items: ArrayList<ChatItem>? = null

    inner class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val messageTextView = itemView.findViewById<AppCompatTextView>(R.id.message_item_id)
        fun bindData(item: ChatItem) {
            messageTextView.text = item.getText()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.messages_item_layout, parent, false)
        return MessagesViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bindData(items?.get(position) ?: return)
    }
}