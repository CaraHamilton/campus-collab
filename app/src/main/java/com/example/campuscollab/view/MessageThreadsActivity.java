package com.example.campuscollab.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.ActivityMessageThreadsBinding;
import com.example.campuscollab.domain.Message;
import com.example.campuscollab.service.MessageService;
import com.example.campuscollab.service.UserService;

import java.util.List;

public class MessageThreadsActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();
    private final MessageService messageService = MessageService.getInstance();

    private ActivityMessageThreadsBinding binding;
    private RecyclerView threadsRecycler;
    private ImageView backArrow;
    private TextView noMessagesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageThreadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noMessagesText = binding.noThreads;
        noMessagesText.setVisibility(View.INVISIBLE);
        backArrow = binding.backArrow;
        threadsRecycler = binding.messageThreadsRecycler;

        try {
            List<Message> threadsList = messageService.getAllThreads().get();
            if (threadsList == null)
            {
                Toast.makeText(this, "Couldn't retrieve any message threads", Toast.LENGTH_SHORT).show();
            } else {
                if (threadsList.size() == 0) {
                    noMessagesText.setVisibility(View.VISIBLE);
                }
                threadsRecycler.setAdapter(new ThreadsAdapter(threadsList));
                threadsRecycler.setLayoutManager(new LinearLayoutManager(threadsRecycler.getContext()));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed_transition = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(feed_transition);
            }
        });
    }


}
