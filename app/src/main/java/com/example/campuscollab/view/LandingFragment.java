package com.example.campuscollab.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentLandingBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.UserService;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LandingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandingFragment extends Fragment {

    private FragmentLandingBinding binding;

    private UserService userService = UserService.getInstance();

    Button registerButton;
    Button signInButton;
    EditText emailField;
    EditText passwordField;

    public LandingFragment() {
        // Required empty public constructor
    }

    public static LandingFragment newInstance() {
        LandingFragment fragment = new LandingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerButton = binding.registerButton;
        signInButton = binding.signInButton;
        emailField = binding.emailEntryBox;
        passwordField = binding.passwordEntryBox;

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LandingFragment.this)
                        .navigate(R.id.action_landingFragment_to_schoolFragment);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                try
                {
                    User user = userService.signIn(email,password).get();

                    if (user == null)
                    {
                        Toast.makeText(getView().getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getView().getContext(), "Sign in succeeded", Toast.LENGTH_SHORT).show();
                        System.out.println("User: " + user.getLastName());

                        Intent feed_transition = new Intent(getActivity(), FeedActivity.class);
                        startActivity(feed_transition);
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}