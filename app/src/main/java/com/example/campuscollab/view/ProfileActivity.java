package com.example.campuscollab.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.databinding.ActivityProfileBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    String userID;
    User user;

    ImageView backArrow;
    ImageView profileHeaderImage;
    ImageView headerPlusSign;
    MaterialCardView userProfilePicture;
    ImageView profilePictureImage;
    TextView userName;
    TextView testText;
    MaterialButton messageButton;

    int SELECT_PROFILE_PIC = 200;
    int SELECT_HEADER_PIC = 300;
    byte[] imageBytes = null;
    byte[] headerBytes = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            userID = bundle.getString("user_id");
        }

        backArrow = binding.backArrow;
        profileHeaderImage = binding.profileHeaderImage;
        headerPlusSign = binding.headerPlusSign;
        userProfilePicture = binding.profilePicture;
        profilePictureImage = binding.profilePictureImage;
        messageButton = binding.messageButton;
        testText = binding.testText;

        userName = binding.userName;

        try {
            user = userService.getAnUser(userID).get();

            if (user == null) {
                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
            } else {
                userName.setText(user.getFirstName() + " " + user.getLastName());

                if (user.getImagePath() != null)
                {
                    imageBytes = userService.getImageBytes(user.getImagePath()).get();
                }

                if (user.getHeaderPath() != null)
                {
                    headerBytes = userService.getImageBytes(user.getHeaderPath()).get();
                }

                if (imageBytes != null)
                {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                    profilePictureImage.setImageDrawable(ob);
                    profilePictureImage.getLayoutParams().height = 325;
                    profilePictureImage.getLayoutParams().width = 325;
                }

                if (headerBytes != null)
                {
                    Bitmap bmp = BitmapFactory.decodeByteArray(headerBytes, 0, headerBytes.length);
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                    profileHeaderImage.setImageDrawable(ob);
                    headerPlusSign.setVisibility(View.GONE);
                }

                if (!userService.getCurrentUser().getId().equals(userID)) {
                    testText.setVisibility(View.GONE);
                }
                else
                {
                    messageButton.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
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
                if (userID.equals(userService.getCurrentUser().getId()))
                {
                    chooseHeaderImage();
                }
            }
        });

        userProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userID.equals(userService.getCurrentUser().getId()))
                {
                    chooseProfileImage();
                }
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Message button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void chooseHeaderImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_HEADER_PIC);
    }

    void chooseProfileImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PROFILE_PIC);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PROFILE_PIC) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    profilePictureImage.setImageURI(selectedImageUri);
                    profilePictureImage.getLayoutParams().height = 300;
                    profilePictureImage.getLayoutParams().width = 300;

                    InputStream iStream = null;
                    try {
                        iStream = getContentResolver().openInputStream(selectedImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        byte[] inputData = getBytes(iStream);
                        userService.uploadImageBytes(userID + "_profile", inputData, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (requestCode == SELECT_HEADER_PIC)
            {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    headerPlusSign.setVisibility(View.GONE);
                    profileHeaderImage.setImageURI(selectedImageUri);
                    profileHeaderImage.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

                    InputStream iStream = null;
                    try {
                        iStream = getContentResolver().openInputStream(selectedImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        byte[] inputData = getBytes(iStream);
                        userService.uploadImageBytes(userID + "_header", inputData, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}