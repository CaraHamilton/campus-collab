package com.example.campuscollab.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.campuscollab.domain.User;
import com.example.campuscollab.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
public class UserService {

    private static UserService instance;
    private final UserRepository userRepository;
    private final AuthService authService;
    private User currentUser;

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

                    currentUser = user;
                    return user;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, User> signIn(String email, String password) {

        AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                try{
                    AsyncTask<Void, Void, FirebaseUser> signInUserTask = authService.signIn(email, password);
                    FirebaseUser authUser = signInUserTask.get();

                    Task<DocumentSnapshot> getUserDocTask = userRepository.getUser(authUser.getUid());
                    DocumentSnapshot documentSnapshot = Tasks.await(getUserDocTask);
                    User user = mapDocToUser(documentSnapshot);
                    currentUser = user;
                    return user;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void signOut() {
        authService.signOut();
        currentUser = null;
    }

    public AsyncTask<Void, Void, User> updateUser(User user) {
        AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                try{
                    Task<Void> updateUserTask = userRepository.updateUser(user);
                    Tasks.await(updateUserTask);
                    currentUser = user;
                    return user;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Boolean> deleteUser(String userId) {
        throw new RuntimeException("deleteUser not implemented");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private User mapDocToUser(DocumentSnapshot documentSnapshot) {
        User user = new User();
        user.setEmail((String) documentSnapshot.get("email"));
        user.setId((String) documentSnapshot.get("id"));
        user.setFirstName((String) documentSnapshot.get("firstName"));
        user.setLastName((String) documentSnapshot.get("lastName"));
        user.setDescription((String) documentSnapshot.get("description"));
        user.setLinkedInUrl((String) documentSnapshot.get("linkedInUrl"));
        user.setMajor((String) documentSnapshot.get("major"));
        user.setPictureUrl((String) documentSnapshot.get("pictureUrl"));
        user.setSkills((List<String>) documentSnapshot.get("skills"));
        user.setResumeUrl((String) documentSnapshot.get("resumeUrl"));
        user.setCreatedDate((Timestamp) documentSnapshot.get("createdDate"));
        user.setSchool((String) documentSnapshot.get("school"));

        return user;
    }

    public AsyncTask<Void, Void, User> getAnUser(String userId) {

        AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                try{
                    Task<DocumentSnapshot> getUserDocTask = userRepository.getUser(userId);
                    DocumentSnapshot documentSnapshot = Tasks.await(getUserDocTask);
                    User user = mapDocToUser(documentSnapshot);

                    return user;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
