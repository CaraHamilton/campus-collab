package com.example.campuscollab.view;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
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
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", feedProjects.get(holder.getAbsoluteAdapterPosition()).getProjectId());
                GroupUserFragment groupUserFragment = new GroupUserFragment();
                groupUserFragment.setArguments(bundle);
                ((FragmentActivity) view.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, groupUserFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedProjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
