package com.example.campuscollab.view;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.ActivityProfileBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.UserService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();

    String userID;
    User user;

    ImageView backArrow;
    ImageView profileHeaderImage;
    MaterialCardView changeHeaderPicCard;
    MaterialCardView userProfilePicture;
    MaterialCardView changePicCard;
    ImageView profilePictureImage;
    TextView userName;
    MaterialButton messageButton;
    Button saveButton;
    EditText userMajor;
    EditText userBio;
    private RecyclerView ownedRecycler;

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
        userProfilePicture = binding.profilePicture;
        profilePictureImage = binding.profilePictureImage;
        changeHeaderPicCard = binding.changeHeaderPicCard;
        changePicCard = binding.changeProfilePicCard;
        messageButton = binding.messageButton;
        ownedRecycler = binding.ownedRecycler;
        saveButton = binding.saveButton;
        userMajor = binding.userMajor;
        userBio = binding.bio;

        userName = binding.userName;

        saveButton.setEnabled(false);
        saveButton.setVisibility(View.INVISIBLE);

        userMajor.addTextChangedListener(textWatcher);
        userBio.addTextChangedListener(textWatcher);

        try {
            user = userService.getAnUser(userID).get();

            if (user == null) {
                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
            } else {
                userName.setText(user.getFirstName() + " " + user.getLastName());
                userMajor.setText("Major");

                if (userMajor != null)
                {
                    userMajor.setText(user.getMajor());
                }

                if (userBio != null)
                {
                    userBio.setText(user.getDescription());
                }

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
                    profilePictureImage.setVisibility(View.VISIBLE);
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
                }

                try {
                    String currentUserId = user.getId();
                    List<Project> ownedProjects = projectService.getProjectsByOwner(currentUserId).get();

                    if (ownedProjects == null)
                    {
                        Toast.makeText(getApplicationContext(), "Couldn't retrieve any projects", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ownedRecycler.setAdapter(new FeedRecyclerViewAdapter(getApplicationContext(), null, null, ownedProjects));
                        ownedRecycler.setLayoutManager(new LinearLayoutManager(ownedRecycler.getContext()));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
                }

                if (!userService.getCurrentUser().getId().equals(userID)) {
                    changePicCard.setVisibility(View.GONE);
                    changeHeaderPicCard.setVisibility(View.GONE);
                    userMajor.setBackground(null);
                    userMajor.setFocusable(false);
                    userBio.setBackground(null);
                    userBio.setFocusable(false);
                    saveButton.setVisibility(View.GONE);
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

        changeHeaderPicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userID.equals(userService.getCurrentUser().getId()))
                {
                    chooseHeaderImage();
                }
            }
        });

        changePicCard.setOnClickListener(new View.OnClickListener() {
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
                //start new activity
                Intent message_transition = new Intent(getApplicationContext(), MessageActivity.class);
                message_transition.putExtra("sender_ID", userID);
                startActivity(message_transition);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User currentUser = userService.getCurrentUser();

                String major = userMajor.getText().toString().trim();
                String bio = userBio.getText().toString().trim();

                if (!major.isEmpty())
                {
                    currentUser.setMajor(major);
                }

                if (!bio.isEmpty())
                {
                    currentUser.setDescription(bio);
                }

                userService.updateUser(currentUser);
                Toast.makeText(getApplicationContext(), "Information Updated", Toast.LENGTH_SHORT).show();
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String major = userMajor.getText().toString().trim();
            String bio = userBio.getText().toString().trim();

            saveButton.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);

            if (!major.isEmpty() || !bio.isEmpty())
            {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = getApplicationContext().getTheme();
                theme.resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
                @ColorInt int color = typedValue.data;

                saveButton.setBackgroundColor(color);
            }
            else
            {
                saveButton.setEnabled(false);
                saveButton.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}