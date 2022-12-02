package com.example.campuscollab.view;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuscollab.R;
import com.example.campuscollab.domain.Project;
import com.example.campuscollab.databinding.FragmentFeedBinding;
import com.example.campuscollab.service.ProjectService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder> {

    private final List<Project> feedProjects;
    private final ProjectService projectService = ProjectService.getInstance();
    private Context context;
    byte[] imageBytes = null;
    ImageView homeIcon;
    TextView homeText;

    public FeedRecyclerViewAdapter(Context context, ImageView homeIcon, TextView homeText, List<Project> items) {
        this.context = context;
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

        byte[] imageBytes = null;

        if (feedProjects.get(position).getImagePath() != null)
        {
            try {
                imageBytes = projectService.getImageBytes(feedProjects.get(position).getImagePath()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (imageBytes != null)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            BitmapDrawable ob = new BitmapDrawable(context.getResources(), bmp);
            holder.image.setImageDrawable(ob);
        }

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
