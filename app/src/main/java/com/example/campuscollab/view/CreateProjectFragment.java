package com.example.campuscollab.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Objects;

public class CreateProjectFragment extends Fragment
                                   implements AdapterView.OnItemSelectedListener {
    private final UserService userService = UserService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();
    private FragmentCreateProjectBinding binding;
    private EditText projectNameInput;
    private EditText projectDescriptionInput;
    private Spinner groupMemberNumber;
    private int maxGroupSize;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCreateProjectBinding.inflate(Objects.requireNonNull(inflater), container, false);
        Button createProjectButton = binding.createProjectButton;
        projectNameInput = binding.projectName;
        projectDescriptionInput = binding.projectDescription;
        groupMemberNumber = binding.groupSizeInput;
        groupMemberNumber.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.group_size_options, R.layout.custom_spinner_element);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupMemberNumber.setAdapter(adapter);

        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidProject()) {
                    Project newProject = new Project(projectNameInput.getText().toString(), userService.getCurrentUser().getId(),
                                                     projectDescriptionInput.getText().toString(), new ArrayList<>(),
                                                     Timestamp.now(), Timestamp.now(), maxGroupSize, null, userService.getCurrentUser().getSchool());

                    try {
                        projectService.createProject(newProject);
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
}
