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
import android.widget.Toast;

import com.example.campuscollab.R;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.service.ProjectService;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class FeedFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ProjectService projectService = ProjectService.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    // TODO: Customize parameter initialization
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
                List<Project> projects = projectService.getAllProjects().get();

                if (projects == null)
                {
                    Toast.makeText(getView().getContext(), "Couldn't retrieve any projects", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setAdapter(new FeedRecyclerViewAdapter(projects));
                }
            } catch(Exception e) {
                Toast.makeText(getView().getContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }
}