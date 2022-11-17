package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscollab.databinding.FragmentParticipatingGroupMemberCardBinding;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParticipatingGroupMemberRecyclerViewAdapter extends RecyclerView.Adapter<ParticipatingGroupMemberRecyclerViewAdapter.ViewHolder> {

    private final List<String> participants;
    private UserService userService = UserService.getInstance();
    private Context context;
    private User clickedUser;
    private byte[] imageBytes = null;

    public ParticipatingGroupMemberRecyclerViewAdapter(Context context, List<String> participants) {
        this.context = context;
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentParticipatingGroupMemberCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.participant = participants.get(position);
        try {
            User user = userService.getAnUser(holder.participant).get();
            clickedUser = user;
            holder.userName.setText(user.getFirstName() + " " + user.getLastName());

            if (user.getImagePath() != null)
            {
                imageBytes = userService.getImageBytes(user.getImagePath()).get();
            }

            if (imageBytes != null)
            {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                BitmapDrawable ob = new BitmapDrawable(context.getResources(), bmp);
                holder.userImage.setImageDrawable(ob);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedUser != null)
                {
                    Intent profile_transition = new Intent(context, ProfileActivity.class);
                    profile_transition.putExtra("user_id", clickedUser.getId());
                    context.startActivity(profile_transition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView userImage;
        public final TextView userName;
        public String participant;

        public ViewHolder(FragmentParticipatingGroupMemberCardBinding binding) {
            super(binding.getRoot());
            userImage = binding.participatingUserImage;
            userName = binding.participatingUserName;
        }
    }
}