package com.example.campuscollab.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.FragmentIncomingMessageBinding;
import com.example.campuscollab.databinding.FragmentOutgoingMessageBinding;
import com.example.campuscollab.domain.Message;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private UserService userService = UserService.getInstance();
    private FragmentOutgoingMessageBinding sentMessageBinding;
    private FragmentIncomingMessageBinding receivedMessageBinding;


    private List<Message> mMessageList;

    public MessageListAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);

        //get current user
        User currentUser = userService.getCurrentUser();

        if (message.getSender().equals(currentUser.getId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            sentMessageBinding = FragmentOutgoingMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SentMessageHolder(sentMessageBinding.getRoot());
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            return new ReceivedMessageHolder(receivedMessageBinding.getRoot());
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = sentMessageBinding.textMessageMe;
            timeText = sentMessageBinding.textTimestampMe;
        }

        void bind(Message message) {
            messageText.setText(message.getContent());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getSentDate().toString());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = receivedMessageBinding.textMessageOther;
            timeText = receivedMessageBinding.textTimestampOther;
            nameText = receivedMessageBinding.textUserOther;
        }

        void bind(Message message) {
            messageText.setText(message.getContent());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getSentDate().toString());

            nameText.setText(message.getSender());
        }
    }
}