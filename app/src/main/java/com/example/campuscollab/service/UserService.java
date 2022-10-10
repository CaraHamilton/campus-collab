package com.example.campuscollab.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.campuscollab.domain.User;
import com.example.campuscollab.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
public class UserService {

    private static UserService instance;
    private final UserRepository userRepository;
    private final AuthService authService;

    public static synchronized UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
        userRepository = new UserRepository();
        authService = AuthService.getInstance();
    }

    public AsyncTask<Void, Void, User> registerUser(User user, String password) {

        AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                try{
                    AsyncTask<Void, Void, FirebaseUser> createAuthUserTask = authService.createUser(user.getEmail(), password);
                    FirebaseUser authUser = createAuthUserTask.get();

                    user.setId(authUser.getUid());

                    Task<Void> createUserDocTask = userRepository.registerUser(user);
                    Tasks.await(createUserDocTask);

                    return user;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, User> updateUser(User user) {
        throw new RuntimeException("updateUser not implemented");
    }

    public AsyncTask<Void, Void, Boolean> deleteUser(String userId) {
        throw new RuntimeException("deleteUser not implemented");
    }

}
