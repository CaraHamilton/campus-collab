package com.example.campuscollab.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.ActivityMainBinding;
import com.example.campuscollab.databinding.FeedPageBinding;
import com.google.firebase.auth.FirebaseUser;

public class FeedActivity extends AppCompatActivity {

    private FeedPageBinding binding;

    ImageView addProjectButton;
    ImageView profileIcon;
    ImageView messageIcon;

    ImageView homeIcon;
    TextView homeText;

    ImageView briefcaseIcon;
    TextView projectText;

    ImageView peopleIcon;
    TextView requestText;

    ImageView settingsIcon;
    TextView settingsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FeedPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addProjectButton = binding.addProjectButton;
        profileIcon = binding.profilePicture;
        messageIcon = binding.chatIcon;

        homeIcon = binding.homeIcon;
        homeText = binding.homeText;

        briefcaseIcon = binding.briefcaseIcon;
        projectText = binding.projectText;

        peopleIcon = binding.peopleIcon;
        requestText = binding.requestText;

        settingsIcon = binding.settingsIcon;
        settingsText = binding.settingsText;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FeedFragment feedFragment = new FeedFragment();
        transaction.replace(R.id.fragment_container, feedFragment);
        transaction.commit();

        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add Project", Toast.LENGTH_SHORT).show();

                homeIcon.setAlpha(.5f);
                homeText.setAlpha(.5f);
                briefcaseIcon.setAlpha(.5f);
                projectText.setAlpha(.5f);
                peopleIcon.setAlpha(.5f);
                requestText.setAlpha(.5f);
                settingsIcon.setAlpha(.5f);
                settingsText.setAlpha(.5f);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CreateProjectFragment createProjectFragment = new CreateProjectFragment();
                transaction.replace(R.id.fragment_container, createProjectFragment);
                transaction.commit();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View User Profile", Toast.LENGTH_SHORT).show();

                //Include fragment transaction here
            }
        });

        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_SHORT).show();

                //Include fragment transaction here
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                homeIcon.setAlpha(1f);
                homeText.setAlpha(1f);
                briefcaseIcon.setAlpha(.5f);
                projectText.setAlpha(.5f);
                peopleIcon.setAlpha(.5f);
                requestText.setAlpha(.5f);
                settingsIcon.setAlpha(.5f);
                settingsText.setAlpha(.5f);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FeedFragment feedFragment = new FeedFragment();
                transaction.replace(R.id.fragment_container, feedFragment);
                transaction.commit();
            }
        });

        briefcaseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Projects", Toast.LENGTH_SHORT).show();

                briefcaseIcon.setAlpha(1f);
                projectText.setAlpha(1f);
                homeIcon.setAlpha(.5f);
                homeText.setAlpha(.5f);
                peopleIcon.setAlpha(.5f);
                requestText.setAlpha(.5f);
                settingsIcon.setAlpha(.5f);
                settingsText.setAlpha(.5f);

                //Include fragment transaction here
            }
        });

        peopleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Requests", Toast.LENGTH_SHORT).show();

                peopleIcon.setAlpha(1f);
                requestText.setAlpha(1f);
                homeIcon.setAlpha(.5f);
                homeText.setAlpha(.5f);
                briefcaseIcon.setAlpha(.5f);
                projectText.setAlpha(.5f);
                settingsIcon.setAlpha(.5f);
                settingsText.setAlpha(.5f);

                //Include fragment transaction here
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();

                settingsIcon.setAlpha(1f);
                settingsText.setAlpha(1f);
                homeIcon.setAlpha(.5f);
                homeText.setAlpha(.5f);
                briefcaseIcon.setAlpha(.5f);
                projectText.setAlpha(.5f);
                peopleIcon.setAlpha(.5f);
                requestText.setAlpha(.5f);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                transaction.replace(R.id.fragment_container, settingsFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}