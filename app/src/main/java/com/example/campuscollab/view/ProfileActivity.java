package com.example.campuscollab.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.ActivityProfileBinding;
import com.example.campuscollab.databinding.FeedPageBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    String userID;
    User user;

    SearchView searchBar;
    ImageView backArrow;
    ImageView profileHeaderImage;
    MaterialCardView userProfilePicture;
    TextView userName;
    TextView testText;
    MaterialButton messageButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.campuscollab.databinding.ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            userID = bundle.getString("user_id");
        }

        searchBar = binding.searchBar;
        backArrow = binding.backArrow;
        profileHeaderImage = binding.profileHeaderImage;
        userProfilePicture = binding.profilePicture;
        messageButton = binding.messageButton;
        testText = binding.testText;

        userName = binding.userName;

        try
        {
            user = userService.getAnUser(userID).get();

            if (user == null)
            {
                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                userName.setText(user.getFirstName() + " " + user.getLastName());
                searchBar.setQuery(user.getFirstName() + " " + user.getLastName(), false);

                if (!userService.getCurrentUser().getId().equals(userID))
                {
                    testText.setVisibility(View.INVISIBLE);
                }
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed_transition = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(feed_transition);
            }
        });

        profileHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Profile header image", Toast.LENGTH_SHORT).show();
            }
        });

        userProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User profile picture", Toast.LENGTH_SHORT).show();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Message button", Toast.LENGTH_SHORT).show();
            }
        });

    }
}