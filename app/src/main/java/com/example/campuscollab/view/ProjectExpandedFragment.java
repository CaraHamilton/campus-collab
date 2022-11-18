package com.example.campuscollab.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentProjectExpandedBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.RequestService;
import com.example.campuscollab.service.UserService;
import com.google.firebase.Timestamp;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 */
public class ProjectExpandedFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private final ProjectService projectService = ProjectService.getInstance();
    private final RequestService requestService = RequestService.getInstance();
    private final UserService userService = UserService.getInstance();

    private FragmentProjectExpandedBinding binding;
    private Project project;
    private ImageView projectPicture;
    private String projectId;
    private String projectTitle;
    private String projectDescription;
    private List<String> participants;

    private Button applyToProjectButton;
    byte[] imageBytes = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProjectExpandedFragment() {
    }

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
                projectPicture = binding.projectPicture;

                if (project.getImagePath() != null)
                {
                    imageBytes = projectService.getImageBytes(project.getImagePath()).get();
                }

                if (imageBytes != null)
                {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                    projectPicture.setImageDrawable(ob);
                }

                projectTitle = project.getProjectName();
                projectDescription = project.getDescription();
                participants = project.getParticipantIds();

                applyToProjectButton = binding.applyToProjectBtn;

                User currentUser = userService.getCurrentUser();
                Request existingRequest = requestService.getCurrentUsersRequestForProject(projectId).get();

                if(existingRequest != null)
                {
                    changeToUndoButton(applyToProjectButton);
                }

                if (currentUser.getId().equals(project.getOwnerId()) || project.getParticipantIds().contains(currentUser.getId())) {
                    applyToProjectButton.setEnabled(false);
                    applyToProjectButton.setVisibility(View.INVISIBLE);
                } else {
                    applyToProjectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if(requestService.getCurrentUsersRequestForProject(projectId).get() != null) {
                                    requestService.deleteRequest(requestService.getCurrentUsersRequestForProject(projectId).get().getId());
                                    changeToApplyButton(applyToProjectButton);
                                }
                                else {
                                    //TODO fill out constructor properly once we have user images and can get users by id
                                    Request req = new Request(project.getProjectId(), project.getProjectName(), project.getOwnerId(),
                                            project.getOwnerId(), "imageurl", currentUser.getId(),
                                            currentUser.getFirstName() + " " + currentUser.getLastName(),
                                            currentUser.getImagePath(), UUID.randomUUID().toString(), RequestService.PENDING_KEY,
                                            Timestamp.now());

                                    requestService.createRequest(req);
                                    changeToUndoButton(applyToProjectButton);
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        } catch (Exception e) {
            Toast.makeText(requireView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
        }

        // Set the adapter
        if (binding.list instanceof RecyclerView) {
            Context context = binding.list.getContext();
            RecyclerView recyclerView = (RecyclerView) binding.list;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ParticipatingGroupMemberRecyclerViewAdapter(getContext(), project.getParticipantIds()));
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.projectTitle.setText(projectTitle);
        binding.description.setText(projectDescription);
    }

    private void changeToUndoButton(Button applyToProjectButton)
    {
        applyToProjectButton.setText("Undo Request");
        applyToProjectButton.setBackgroundColor(getContext().getResources().getColor(R.color.reject_red));
    }

    private void changeToApplyButton(Button applyToProjectButton)
    {
        applyToProjectButton.setText("Request to join project");
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorButtonNormal, typedValue, true);
        @ColorInt int color = typedValue.data;
        applyToProjectButton.setBackgroundColor(color);
    }

}
