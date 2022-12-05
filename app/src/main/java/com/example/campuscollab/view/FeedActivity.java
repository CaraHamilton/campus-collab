package com.example.campuscollab.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FeedPageBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.RequestService;
import com.example.campuscollab.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class FeedActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();
    private final RequestService requestService = RequestService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();
    private List<Project> projectList;

    SearchView searchBar;

    CardView addProjectButton;
    ImageView profileIcon;
    ImageView messageIcon;

    ImageView homeIcon;
    TextView homeText;

    ImageView projectIcon;
    TextView projectText;

    ImageView requestIcon;
    TextView requestText;
    CardView pendingRequestIcon;

    ImageView settingsIcon;
    TextView settingsText;

    byte[] imageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.campuscollab.databinding.FeedPageBinding binding = FeedPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchBar = binding.searchBar;
        searchBar.clearFocus();

        addProjectButton = binding.addProjectButton;
        profileIcon = binding.profilePicture;
        messageIcon = binding.chatIcon;

        homeIcon = binding.homeIcon;
        homeText = binding.homeText;

        projectIcon = binding.briefcaseIcon;
        projectText = binding.projectText;

        requestIcon = binding.peopleIcon;
        requestText = binding.requestText;
        pendingRequestIcon = binding.pendingRequestIcon;

        settingsIcon = binding.settingsIcon;
        settingsText = binding.settingsText;

        try {
            User currentUser = userService.getCurrentUser();
            projectList = projectService.getAllProjects().get();
            List<Request> incomingRequestsList = requestService.getSentRequests().get();

            if (incomingRequestsList.size() != 0)
            {
                pendingRequestIcon.setVisibility(View.VISIBLE);
            }

            if (currentUser.getImagePath() != null)
            {
                imageBytes = userService.getImageBytes(currentUser.getImagePath()).get();
            }

            if (imageBytes != null)
            {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                profileIcon.setImageDrawable(ob);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (userService.isFromSettings)
        {
            changeOpacity(settingsIcon, settingsText);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SettingsFragment settingsFragment = new SettingsFragment();
            transaction.replace(R.id.fragment_container, settingsFragment);
            transaction.commit();
        }
        else
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FeedFragment feedFragment = new FeedFragment();
            transaction.replace(R.id.fragment_container, feedFragment);
            transaction.commit();
        }

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Bundle text = new Bundle();
                text.putString("search_text", newText);

                lowerOpacity();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FeedFragment feedFragment = new FeedFragment();
                feedFragment.setArguments(text);
                transaction.replace(R.id.fragment_container, feedFragment);
                transaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                lowerOpacity();
                userService.isFromSettings = false;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CreateProjectFragment createProjectFragment = new CreateProjectFragment();
                transaction.replace(R.id.fragment_container, createProjectFragment);
                transaction.commit();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                Intent feed_transition = new Intent(getApplicationContext(), ProfileActivity.class);
                feed_transition.putExtra("user_id", userService.getCurrentUser().getId());
                startActivity(feed_transition);
            }
        });

        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                userService.isFromSettings = false;
                Intent messageThreads = new Intent(getApplicationContext(), MessageThreadsActivity.class);
                startActivity(messageThreads);
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                searchBar.setQuery("", false);
                changeOpacity(homeIcon, homeText);
                userService.isFromSettings = false;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FeedFragment feedFragment = new FeedFragment();
                transaction.replace(R.id.fragment_container, feedFragment);
                transaction.commit();
            }
        });

        projectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                changeOpacity(projectIcon, projectText);
                userService.isFromSettings = false;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ProjectsFragment projectsFragment = new ProjectsFragment();
                transaction.replace(R.id.fragment_container, projectsFragment);
                transaction.commit();
            }
        });

        requestIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                changeOpacity(requestIcon, requestText);
                userService.isFromSettings = false;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RequestsFragment requestsFragment = new RequestsFragment();
                transaction.replace(R.id.fragment_container, requestsFragment);
                transaction.commit();
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.clearFocus();
                changeOpacity(settingsIcon, settingsText);
                userService.isFromSettings = false;

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

    public ImageView getHomeIcon()
    {
        return homeIcon;
    }
    public TextView getHomeText()
    {
        return homeText;
    }

    public String getSearchText()
    {
        return searchBar.getQuery().toString();
    }

    public CardView getPendingRequestIcon() { return pendingRequestIcon; }

    @Override
    public void onBackPressed() {
        //TODO determine which fragment was being shown and enable switching back
    }
}