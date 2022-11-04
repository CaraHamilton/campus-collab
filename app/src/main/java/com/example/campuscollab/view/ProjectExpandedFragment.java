package com.example.campuscollab.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.campuscollab.databinding.FragmentProjectExpandedBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.RequestService;
import com.example.campuscollab.service.UserService;
import com.google.firebase.Timestamp;

import java.util.UUID;

/**
 * A fragment representing a list of Items.
 */
public class ProjectExpandedFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private final ProjectService projectService = ProjectService.getInstance();
    private final RequestService requestService = RequestService.getInstance();
    private final UserService userService = UserService.getInstance();

    private FragmentProjectExpandedBinding binding;
    private Project project;
    private String projectId;
    private String projectTitle;
    private String projectDescription;
    private Button applyToProjectButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProjectExpandedFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProjectExpandedFragment newInstance(int columnCount) {
        ProjectExpandedFragment fragment = new ProjectExpandedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            projectId = getArguments().getString("project_id");
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectExpandedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        try {
            project = projectService.getProject(projectId).get();

            if (project == null) {
                Toast.makeText(requireView().getContext(), "Couldn't retrieve project", Toast.LENGTH_SHORT).show();
            } else {
                projectTitle = project.getProjectName();
                projectDescription = project.getDescription();

                applyToProjectButton = binding.applyToProjectBtn;

                User currentUser = userService.getCurrentUser();

                if (currentUser.getId().equals(project.getOwnerId())) {
                    applyToProjectButton.setEnabled(false);
                    applyToProjectButton.setVisibility(View.INVISIBLE);
                } else {

                    applyToProjectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO fill out constructor properly once we have user images and can get users by id
                            Request req = new Request(project.getProjectId(), project.getProjectName(), project.getOwnerId(),
                                    project.getOwnerId(), "imageurl", currentUser.getId(),
                                    currentUser.getFirstName() + " " + currentUser.getLastName(),
                                    currentUser.getPictureUrl(), UUID.randomUUID().toString(), RequestService.PENDING_KEY,
                                    Timestamp.now());

                            requestService.createRequest(req);

                            Toast.makeText(view.getContext(), "Request sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        } catch (Exception e) {
            Toast.makeText(requireView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
        }

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new GroupMemberRecyclerViewAdapter(PlaceholderContent.ITEMS));
//        }
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.projectTitle.setText(projectTitle);
        binding.description.setText(projectDescription);
    }
}
