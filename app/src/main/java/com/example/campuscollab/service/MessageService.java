package com.example.campuscollab.service;

import static com.google.android.gms.tasks.Tasks.await;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.recyclerview.widget.SortedList;

import com.example.campuscollab.domain.Message;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.repository.MessageRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
public class MessageService {

    private static MessageService instance;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public static synchronized MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    private MessageService() {
        messageRepository = new MessageRepository();
        userService = UserService.getInstance();
    }

    public AsyncTask<Void, Void, Message> sendMessage(Message message) {
        AsyncTask<Void, Void, Message> task = new AsyncTask<Void, Void, Message>() {
            @Override
            protected Message doInBackground(Void... voids) {
                try {
                    Task<Void> createMessageTask = messageRepository.createMessage(message);
                    await(createMessageTask);

                    return message;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Message>> getMessagesWithUser(String userId) {
        AsyncTask<Void, Void, List<Message>> task = new AsyncTask<Void, Void, List<Message>>() {
            @Override
            protected List<Message> doInBackground(Void... voids) {
                try {
                    User currentUser = userService.getCurrentUser();

                    // get sent messages
                    Task<QuerySnapshot> getSentMessagesTask = messageRepository.getMessages(currentUser.getId(), userId);
                    QuerySnapshot sentQuerySnapshot = Tasks.await(getSentMessagesTask);
                    List<DocumentSnapshot> sentDocuments = sentQuerySnapshot.getDocuments();

                    List<Message> messages = new ArrayList<>();

                    for(DocumentSnapshot document: sentDocuments) {
                        messages.add(mapDocToMessage(document));
                    }

                    // get received messages
                    Task<QuerySnapshot> getReceivedMessagesTask = messageRepository.getMessages(userId, currentUser.getId());
                    QuerySnapshot receivedQuerySnapshot = Tasks.await(getReceivedMessagesTask);
                    List<DocumentSnapshot> receivedDocuments = receivedQuerySnapshot.getDocuments();

                    for(DocumentSnapshot document: receivedDocuments) {
                        messages.add(mapDocToMessage(document));
                    }

                    // combine and sort
                    Collections.sort(messages, new MessageComparator());

                    return messages;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private Message mapDocToMessage(DocumentSnapshot documentSnapshot) {
        Message message = new Message();
        message.setId((String) documentSnapshot.get("id"));
        message.setSender((String) documentSnapshot.get("sender"));
        message.setReceiver((String) documentSnapshot.get("receiver"));
        message.setContent((String) documentSnapshot.get("content"));
        message.setSentDate((Timestamp) documentSnapshot.get("sentDate"));

        return message;
    }

    private class MessageComparator implements Comparator<Message> {
        public int compare(Message ob1, Message ob2) {
            return ob1.getSentDate().compareTo(ob2.getSentDate());
        }
    }

}
