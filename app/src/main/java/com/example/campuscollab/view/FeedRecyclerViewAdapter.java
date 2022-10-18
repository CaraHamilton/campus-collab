package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.domain.Project;
import com.example.campuscollab.databinding.FragmentFeedBinding;

import java.util.List;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder> {

    private final List<Project> feedProjects;

    public FeedRecyclerViewAdapter(List<Project> items) {
        feedProjects = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(feedProjects.get(position).getProjectName());
        holder.description.setText(feedProjects.get(position).getDescription());
//        holder.image.setImageDrawable( get user image based on project owner );
    }

    @Override
    public int getItemCount() {
        return feedProjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;

        public ViewHolder(FragmentFeedBinding binding) {
            super(binding.getRoot());
            title = binding.title;
            description = binding.description;
            image = binding.projectImage;
        }
    }
}
