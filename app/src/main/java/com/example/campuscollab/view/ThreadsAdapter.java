package com.example.campuscollab.view;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.MessageThreadItemBinding;
import com.example.campuscollab.domain.Message;
import com.example.campuscollab.service.UserService;

import java.util.List;

public class ThreadsAdapter extends RecyclerView.Adapter<ThreadsAdapter.ThreadViewHolder> {
    private List<Message> threads;

    public ThreadsAdapter(List<Message> threads) {
        this.threads = threads;
    }

    @NonNull
    @Override
    public ThreadsAdapter.ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThreadsAdapter.ThreadViewHolder(MessageThreadItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadsAdapter.ThreadViewHolder holder, int position) {
        Message currThread = threads.get(position);
        String other_user_id;
        if (currThread.getSender().equals(UserService.getInstance().getCurrentUser().getId())) {
            holder.name.setText(currThread.getReceiverName());
            other_user_id = currThread.getReceiver();
        } else {
            holder.name.setText(currThread.getSenderName());
            other_user_id = currThread.getSender();
        }
        holder.latestMessage.setText(currThread.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:: call new activity of that specific thread
                Intent conversation = new Intent(view.getContext(), MessageActivity.class);
                conversation.putExtra("sender_ID", other_user_id);
                Bundle bundle = new Bundle();
                startActivity(view.getContext(), conversation, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public class ThreadViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView latestMessage;
        public ImageView image;

        public ThreadViewHolder(MessageThreadItemBinding binding) {
            super(binding.getRoot());
            image = binding.profilePic;
            name = binding.threadName;
            latestMessage = binding.latestMessage;
        }
    }
}
