package com.example.campuscollab.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
public class AuthService {

    private static AuthService instance;
    private final FirebaseAuth auth;

    private AuthService() {
        auth = FirebaseAuth.getInstance();
    }

    public static synchronized AuthService getInstance() {
        if(instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public AsyncTask<Void, Void, FirebaseUser> signIn(String email, String password) {
        AsyncTask<Void, Void, FirebaseUser> task = new AsyncTask<Void, Void, FirebaseUser>() {
            @Override
            protected FirebaseUser doInBackground(Void... voids) {
                try{
                    Task<AuthResult> signInTask = auth.signInWithEmailAndPassword(email, password);
                    AuthResult result = Tasks.await(signInTask);
                    return result.getUser();
                } catch (ExecutionException | InterruptedException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void signOut() {
        auth.signOut();
    }


    public AsyncTask<Void, Void, FirebaseUser> createUser(String email, String password) {
        AsyncTask<Void, Void, FirebaseUser> task = new AsyncTask<Void, Void, FirebaseUser>() {
            @Override
            protected FirebaseUser doInBackground(Void... voids) {
                try{
                    Task<AuthResult> createUserTask = auth.createUserWithEmailAndPassword(email, password);
                    AuthResult result = Tasks.await(createUserTask);
                    return result.getUser();
                } catch (ExecutionException | InterruptedException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Void> deleteUser(String userId) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Task<Void> deleteUserTask = auth.getCurrentUser().delete();
                    Void result = Tasks.await(deleteUserTask);
                    return result;
                } catch (ExecutionException | InterruptedException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
