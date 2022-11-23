package com.example.campuscollab.view;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.R;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.databinding.FragmentFeedBinding;

import java.util.List;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder> {

    private final List<Project> feedProjects;
    ImageView homeIcon;
    TextView homeText;

    public FeedRecyclerViewAdapter(ImageView homeIcon, TextView homeText, List<Project> items) {
        this.homeIcon = homeIcon;
        this.homeText = homeText;
        feedProjects = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(feedProjects.get(position).getProjectName());
        holder.description.setText(feedProjects.get(position).getDescription());
//        holder.image.setImageDrawable( get user image based on project owner );
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIcon.setAlpha(.5f);
                homeText.setAlpha(.5f);
                Bundle bundle = new Bundle();
                bundle.putString("project_id", feedProjects.get(holder.getAbsoluteAdapterPosition()).getProjectId());
                ProjectExpandedFragment projectExpandedFragment = new ProjectExpandedFragment();
                projectExpandedFragment.setArguments(bundle);
                ((FragmentActivity) view.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, projectExpandedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedProjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
        public CardView card;

        public ViewHolder(FragmentFeedBinding binding) {
            super(binding.getRoot());
            title = binding.title;
            description = binding.description;
            image = binding.projectImage;
            card = binding.projectCard;
        }
    }
}
