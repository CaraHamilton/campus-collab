package com.example.campuscollab.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscollab.R;
import com.example.campuscollab.databinding.FragmentProjectsBinding;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.service.ProjectService;
import com.example.campuscollab.service.UserService;

import java.util.List;

public class ProjectsFragment extends Fragment {
    private FragmentProjectsBinding binding;
    private TextView ownedProjects;
    private TextView participatingProjects;
    private RecyclerView ownedRecycler;
    private RecyclerView participatingRecycler;

    private final ProjectService projectService = ProjectService.getInstance();
    private final UserService userService = UserService.getInstance();

    public ProjectsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);

        ownedProjects = binding.ownedProjects;
        participatingProjects = binding.participatingProjects;
        ownedRecycler = binding.ownedRecycler;
        participatingRecycler = binding.participatingRecycler;


        try {
            String currentUserId = userService.getCurrentUser().getId();
            List<Project> ownedProjects = projectService.getProjectsByOwner(currentUserId).get();

            if (ownedProjects == null)
            {
                Toast.makeText(requireView().getContext(), "Couldn't retrieve any projects", Toast.LENGTH_SHORT).show();
            } else {
                ownedRecycler.setAdapter(new FeedRecyclerViewAdapter(ownedProjects));
                ownedRecycler.setLayoutManager(new LinearLayoutManager(ownedRecycler.getContext()));
            }

            List<Project> participatingProjects = projectService.getProjectsByParticipant(currentUserId).get();

            if (participatingProjects == null)
            {
                Toast.makeText(requireView().getContext(), "Couldn't retrieve any projects", Toast.LENGTH_SHORT).show();
            } else {
                participatingRecycler.setAdapter(new FeedRecyclerViewAdapter(participatingProjects));
                participatingRecycler.setLayoutManager(new LinearLayoutManager(participatingRecycler.getContext()));
            }

        } catch (Exception e) {
            Toast.makeText(requireView().getContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        this.participatingProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeColor = getActiveColor();
                int inactiveColor = getInactiveColor();

                ownedRecycler.setVisibility(View.INVISIBLE);
                ownedProjects.setTextColor(inactiveColor);
                participatingProjects.setTextColor(activeColor);
                participatingRecycler.setVisibility(View.VISIBLE);
            }
        });

        this.ownedProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeColor = getActiveColor();
                int inactiveColor = getInactiveColor();

                participatingRecycler.setVisibility(View.INVISIBLE);
                participatingProjects.setTextColor(inactiveColor);
                ownedProjects.setTextColor(activeColor);
                ownedRecycler.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    private int getActiveColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorControlActivated, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }

    private int getInactiveColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorControlNormal, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
