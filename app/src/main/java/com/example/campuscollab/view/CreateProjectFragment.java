package com.example.campuscollab.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentCreateProjectBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.service.AuthService;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.UserService;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateProjectFragment extends Fragment
                                   implements AdapterView.OnItemSelectedListener {
    private AuthService authService = AuthService.getInstance();
    private ProjectService projectService = ProjectService.getInstance();
    private FragmentCreateProjectBinding binding;
    private Button createProjectButton;
    private EditText projectNameInput;
    private EditText projectDescriptionInput;
    private Spinner groupMemberNumber;
    private int maxGroupSize;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCreateProjectBinding.inflate(inflater, container, false);
        createProjectButton = binding.createProjectButton;
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
                    Project newProject = new Project(projectNameInput.getText().toString(), authService.getCurrentUser().getUid(),
                                                     projectDescriptionInput.getText().toString(), new ArrayList<>(),
                                                     Timestamp.now(), Timestamp.now(), maxGroupSize);

                    //TODO fix as needed once createProject is implemented
                    //TODO add toast for project created
                    try {
                        projectService.createProject(newProject);
                    } catch (Exception e) {
                        Toast.makeText(getView().getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT);
                    }

                } else {
                    Toast.makeText(getView().getContext(), "Please provide a name and description for your project", Toast.LENGTH_SHORT);
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
