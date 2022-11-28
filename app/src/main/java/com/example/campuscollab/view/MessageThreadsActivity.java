package com.example.campuscollab.view;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageThreadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        threadsRecycler = binding.messageThreadsRecycler;

        try {
            List<Message> threadsList = messageService.getMessagesWithUser(userService.getCurrentUser().getId()).get();
            if (threadsList == null)
            {
                Toast.makeText(this, "Couldn't retrieve any message threads", Toast.LENGTH_SHORT).show();
            } else {
                threadsRecycler.setAdapter(new ThreadsAdapter(threadsList));
                threadsRecycler.setLayoutManager(new LinearLayoutManager(threadsRecycler.getContext()));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }


}
