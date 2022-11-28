package com.example.campuscollab.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentCreateProjectBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.UserService;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.Timestamp;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CreateProjectFragment extends Fragment
                                   implements AdapterView.OnItemSelectedListener {
    private final UserService userService = UserService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();
    private FragmentCreateProjectBinding binding;
    private MaterialCardView card;
    private ImageView projectImage;
    private EditText projectNameInput;
    private EditText projectDescriptionInput;
    private Spinner groupMemberNumber;
    private int maxGroupSize;

    int SELECT_PROJECT_PIC = 200;
    byte[] imageBytes = null;
    Uri selectedImageUri = null;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCreateProjectBinding.inflate(Objects.requireNonNull(inflater), container, false);
        Button createProjectButton = binding.createProjectButton;
        card = binding.projectCard;
        projectImage = binding.projectPicture;
        projectNameInput = binding.projectName;
        projectDescriptionInput = binding.projectDescription;
        groupMemberNumber = binding.groupSizeInput;
        groupMemberNumber.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.group_size_options, R.layout.custom_spinner_element);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupMemberNumber.setAdapter(adapter);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProjectImage();
            }
        });

        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidProject()) {
                    Project newProject = new Project(projectNameInput.getText().toString(), userService.getCurrentUser().getId(),
                                                     projectDescriptionInput.getText().toString(), new ArrayList<>(),
                                                     Timestamp.now(), Timestamp.now(), maxGroupSize, userService.getCurrentUser().getId() + "_project", userService.getCurrentUser().getSchool());

                    try {
                        projectService.createProject(newProject);
                        List<String> participants = newProject.getParticipantIds();
                        participants.add(userService.getCurrentUser().getId());
                        newProject.setParticipantIds(participants);
                        projectService.updateProject(newProject).get();

                        if (imageBytes != null)
                        {
                            projectService.uploadImageBytes(newProject, newProject.getProjectId() + "_project", imageBytes);
                        }

                        Toast.makeText(requireView().getContext(), "Project created!", Toast.LENGTH_SHORT).show();
                        FeedFragment feedFragment = new FeedFragment();
                        ((FragmentActivity) view.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, feedFragment)
                                .addToBackStack(null)
                                .commit();
                    } catch (Exception e) {
                        Toast.makeText(requireView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireView().getContext(), "Please provide a name and description for your project", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isValidProject() {
        return (projectNameInput.getText().length() > 0) && (projectDescriptionInput.getText().length() > 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        maxGroupSize = getResources().getIntArray(R.array.group_size_values)[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        int[] options = getResources().getIntArray(R.array.group_size_values);
        maxGroupSize = options[options.length - 1];
        groupMemberNumber.setSelection(options.length - 1);
    }

    void chooseProjectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PROJECT_PIC);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PROJECT_PIC) {
            selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                projectImage.setImageURI(selectedImageUri);
                projectImage.getLayoutParams().height = 300;
                projectImage.getLayoutParams().width = 300;

                InputStream iStream = null;
                try {
                    iStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    imageBytes = getBytes(iStream);
                } catch (IOException e) {
                    e.printStackTrace();
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
