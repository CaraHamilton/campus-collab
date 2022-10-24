package com.example.campuscollab.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentProjectExpandedBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.view.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class GroupUserFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ProjectService projectService = ProjectService.getInstance();

    private FragmentProjectExpandedBinding binding;
    private Project project;
    private String projectId;
    private String projectTitle;
    private String projectDescription;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupUserFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroupUserFragment newInstance(int columnCount) {
        GroupUserFragment fragment = new GroupUserFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectExpandedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        try {
            project = projectService.getProject(projectId).get();

            if (project == null) {
                Toast.makeText(getView().getContext(), "Couldn't retrieve project", Toast.LENGTH_SHORT).show();
            } else {
                projectTitle = project.getProjectName();
                projectDescription = project.getDescription();
            }

        } catch (Exception e) {
            Toast.makeText(getView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
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
