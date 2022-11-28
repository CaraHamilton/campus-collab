package com.example.campuscollab.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.campuscollab.domain.Message;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.MessageService;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.databinding.ActivityMessageBinding;
import com.example.campuscollab.service.UserService;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMessageBinding binding;
    private MessageService messageService = MessageService.getInstance();
    private UserService userService = UserService.getInstance();
    private String incomingMessageUserID;
    private MessageListAdapter messageListAdapter;

    private RecyclerView recyclerView;
    private TextView messageText;
    private Button sendButton;

    private User currentUser;
    private User incomingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get information from bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            incomingMessageUserID = extras.getString("userID");
            //The key argument here must match that used in the other activity
        }

        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerGchat;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            currentUser = userService.getCurrentUser();
            incomingUser = userService.getAnUser(incomingMessageUserID).get();

            List<Message> messages = messageService.getMessagesWithUser(incomingMessageUserID).get();
            //To Do: sort messages by date
            messageListAdapter = new MessageListAdapter(messages);

            recyclerView.setAdapter(messageListAdapter);
        } catch (Exception e) {
            System.out.println("Error getting messages");
        }

        sendButton = binding.buttonSend;
        sendButton.setOnClickListener(v -> {
            messageText = binding.editMessage;
            String messageContent = messageText.getText().toString();
            Timestamp timestamp = new Timestamp(new Date());
            Message newMessage = new Message(currentUser.getId(), incomingUser.getId(), messageContent, timestamp);
            try {
                messageService.sendMessage(newMessage);
            } catch (Exception e) {
                System.out.println("Error sending message");
            }
            messageText.setText("");

            //refresh the recycler view
            try {
                List<Message> messages = messageService.getMessagesWithUser(incomingMessageUserID).get();
                messageListAdapter = new MessageListAdapter(messages);
                recyclerView.setAdapter(messageListAdapter);
            } catch (Exception e) {
                System.out.println("Error getting messages");
            }
        });
    }

    public List<Message> sortMessages(List<Message> messages) {
        List<Message> sortedMessages = new ArrayList<>(messages);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(sortedMessages, Comparator.comparing(Message::getSentDate));
        }

        return sortedMessages;
    }
}