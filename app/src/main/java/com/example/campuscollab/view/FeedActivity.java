package com.example.campuscollab.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FeedPageBinding;

public class FeedActivity extends AppCompatActivity {

    ImageView addProjectButton;
    ImageView profileIcon;
    ImageView messageIcon;

    ImageView homeIcon;
    TextView homeText;

    ImageView projectIcon;
    TextView projectText;

    ImageView requestIcon;
    TextView requestText;

    ImageView settingsIcon;
    TextView settingsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.campuscollab.databinding.FeedPageBinding binding = FeedPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addProjectButton = binding.addProjectButton;
        profileIcon = binding.profilePicture;
        messageIcon = binding.chatIcon;

        homeIcon = binding.homeIcon;
        homeText = binding.homeText;

        projectIcon = binding.briefcaseIcon;
        projectText = binding.projectText;

        requestIcon = binding.peopleIcon;
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
                lowerOpacity();

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

                //TODO Include switch to profile activity
            }
        });

        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_SHORT).show();

                //TODO Include switch to message activity
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                changeOpacity(homeIcon, homeText);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FeedFragment feedFragment = new FeedFragment();
                transaction.replace(R.id.fragment_container, feedFragment);
                transaction.commit();
            }
        });

        projectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Projects", Toast.LENGTH_SHORT).show();

                changeOpacity(projectIcon, projectText);

                //TODO include transaction to project fragment
            }
        });

        requestIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Requests", Toast.LENGTH_SHORT).show();

                changeOpacity(requestIcon, requestText);

                //TODO include transaction to request fragment
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();

                changeOpacity(settingsIcon, settingsText);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                transaction.replace(R.id.fragment_container, settingsFragment);
                transaction.commit();
            }
        });
    }

    private void changeOpacity(ImageView image, TextView text)
    {
        lowerOpacity();

        image.setAlpha(1f);
        text.setAlpha(1f);
    }

    private void lowerOpacity()
    {
        homeIcon.setAlpha(.5f);
        homeText.setAlpha(.5f);
        projectIcon.setAlpha(.5f);
        projectText.setAlpha(.5f);
        requestIcon.setAlpha(.5f);
        requestText.setAlpha(.5f);
        settingsIcon.setAlpha(.5f);
        settingsText.setAlpha(.5f);
    }

    @Override
    public void onBackPressed() {
        //TODO determine which fragment was being shown and enable switching back
    }
}