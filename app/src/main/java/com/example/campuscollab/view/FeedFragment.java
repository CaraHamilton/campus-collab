package com.example.campuscollab.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ProjectService projectService = ProjectService.getInstance();
    private UserService userService = UserService.getInstance();
    private String school;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    @SuppressWarnings("unused")
    public static FeedFragment newInstance(int columnCount) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        FeedActivity feedActivity = (FeedActivity) getActivity();
        ImageView homeIcon = feedActivity.getHomeIcon();
        TextView homeText = feedActivity.getHomeText();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            try {
                school = userService.getCurrentUser().getSchool();
//                List<Project> projects = projectService.getProjectsBySchool(school).get();
                List<Project> projects = projectService.getAllProjects().get();
                List<Project> sortedProjects = sortProjects(projects);
                if (projects == null)
                {
                    Toast.makeText(requireView().getContext(), "Couldn't retrieve any projects", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setAdapter(new FeedRecyclerViewAdapter(homeIcon, homeText, sortedProjects));
                }
            } catch(Exception e) {
                Toast.makeText(requireView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }

    public List<Project> sortProjects(List<Project> projects) {
        List<Project> sortedProjects = new ArrayList<>(projects);
        Collections.sort(sortedProjects, Collections.reverseOrder());

        return sortedProjects;
    }
}